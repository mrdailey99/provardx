import ProvarDXUtility from '../../utilities/ProvarDXUtility';
import { execSync } from 'child_process';
import { Messages } from '@salesforce/core';
import { SfdxCommand, flags } from '@salesforce/command';
import { AnyJson } from '@salesforce/ts-types';


/**
 * Command to compile all source files. (PageObjects, CustomAPIs)
 * Class will look out for file to compile in project home src directory.
 * @author Himanshu Sharma
 * 
 */

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file.
const messages = Messages.loadMessages('@provartesting/provardx', 'compile');
export default class compile extends SfdxCommand {

    public static description = messages.getMessage('commandDescription');
    public static examples = [
      `$ sfdx provar:compile -p './myproperties.json' --json --loglevel SEVERE`
    ];

    protected static flagsConfig = {
      propertyfile: flags.string({char: 'p', description: messages.getMessage('propertyFileFlagDescription')}),
      provarhome: flags.string({char: 'h', description: messages.getMessage('provarHomeFlagDescription')}),
      projectpath: flags.string({char: 'c', description: messages.getMessage('projectPathFlagDescription')}),
      loglevel: flags.string({char: 'l', description: messages.getMessage('loglevelFlagDescription')})
    };
    
    public static args = [{name: 'file'}];

    public async run(): Promise<AnyJson> {
        const propertyFile : string = this.flags.propertyfile;
        const json : boolean = this.flags.json;
        const loglevel : string = this.flags.loglevel ? this.flags.loglevel : 'INFO';
        const provarHome : string = this.flags.provarhome;
        const projectPath : string = this.flags.projectpath;

        let provarDxUtils : ProvarDXUtility = new ProvarDXUtility();
        let isValid : boolean = provarDxUtils.validatePropertiesJson(propertyFile);
        let jsonValue = json ? json : false;

        if(!isValid) {
            this.ux.error("Invalid property file. Run command sfdx provar:validate -e true' to get the validation errors");
            return {};
        }
        
        this.ux.log("Provided property file:" + propertyFile);
        this.ux.log("Json: " + jsonValue);
        this.ux.log("LogLevel: " + loglevel);

        let properties = this.updatePropertiesWithOverrides(provarDxUtils.getProperties(), provarHome, projectPath);
        let rawProperties = JSON.stringify(properties);

        if(properties.provarHome == null) {
          this.ux.error('Prover home is not specified.');
          return {};
        }

        let updateProperties = provarDxUtils.prepareRawProperties(rawProperties);
        let jarPath = properties.provarHome +'/provardx/provardx.jar';
        execSync('java -cp "' + jarPath + '" com.provar.provardx.DxCommandExecuter ' + updateProperties + " " + "NA" + " " + "Compile", 
          {stdio: 'inherit'});

        return {};
    }

    public updatePropertiesWithOverrides(properties: any, provarHome: string, projectPath: string) {
      properties.provarHome = provarHome == null ? properties.provarHome : provarHome;
      properties.projectPath = projectPath == null ? properties.projectPath: projectPath;
      return properties;
    }
}
  