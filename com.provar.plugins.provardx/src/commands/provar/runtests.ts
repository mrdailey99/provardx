import ProvarDXUtility from '../../utilities/ProvarDXUtility';
import { execSync } from 'child_process';
import { Messages } from '@salesforce/core';
import { SfdxCommand, flags } from '@salesforce/command';
import { AnyJson } from '@salesforce/ts-types';
import { cli } from 'cli-ux';


/**
 * Runs the specified list of Provar test cases against the currently configured SFDX defaultuserrname unless overridden
 * in the command below or by a provardx-properties.json file
 * @author Himanshu Sharma
 * 
 */

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file.
const messages = Messages.loadMessages('@provartesting/provardx', 'runtests');
export default class runtests extends SfdxCommand {

    public static description = messages.getMessage('commandDescription');
    public static examples = [
      `$ sfdx provar:runtests -f './myproperties.json'`
    ];
   
      
    protected static flagsConfig = {
      // flag with a value (-f, --filespec=VALUE)
      filespec: flags.string({char: 'f', description: messages.getMessage('fileSpecFlagDescription')}),
      // flag with a value (-p, --propertyfile=VALUE)
      propertyfile: flags.string({char: 'p', description: messages.getMessage('propertyFileFlagDescription')}),
      // flag with a value (-o, --connectionoverridefile=VALUE)
      connectionoverridefile: flags.string({char: 'o', description: messages.getMessage('connectionOverridefile')}),
      // flag with a value (-c, --cachepath=VALUE)
      cachepath: flags.string({char: 'c', description: messages.getMessage('cachePathFlagDescription')}),
      // flag with a value (-m, --metadatalevel=VALUE)
      metadatalevel: flags.string({char: 'm', description: messages.getMessage('metadataLevelFlagDescription')}),
      // flag with a value (-s, --secrets=VALUE)
      secrets: flags.string({char: 's', description: messages.getMessage('secretsFlagDescription')}),
      // flag with a value (-l, --loglevel VALUE)
      loglevel: flags.string({char: 'l', description: messages.getMessage('loglevelFlagDescription')})
    };
  
    public static args = [{name: 'file'}];

    public async run(): Promise<AnyJson> {
      //const fileSpec : string = this.flags.filespec;
      const propertyFile : string = this.flags.propertyfile;
      //const connectionOverrideFile : string = this.flags.connectionoverridefile;
      const cachePath : string = this.flags.cachepath;
      const metadataLevel : string = this.flags.metadatalevel;
      //const secrets : string = this.flags.secrets;
      //const logLevel : string = this.flags.loglevel;
      //const json : string = this.flags.json;

     
      let provarDxUtils : ProvarDXUtility = new ProvarDXUtility();
      let isValid : boolean = provarDxUtils.validatePropertiesJson(propertyFile);
      
      if(provarDxUtils.getProperties().testPlan && provarDxUtils.getProperties().connectionOverride) {
        const selection = await cli.prompt( 'Test plans detected, connection overrides will be ignored, do you wish to continue (Y/N)? ' );
        if(selection === 'n' || selection === 'n') {
          return {};
        }
      }
      
      if(!isValid || provarDxUtils.hasDuplicateConnectionOverride(provarDxUtils.getProperties())) {
          this.ux.error("Invalid property file. Run command sfdx provar:validate' to know the validation errors");
          return {};
      }
      
      let properties = this.updatePropertiesWithOverrides(provarDxUtils.getProperties(), metadataLevel, cachePath, propertyFile);
      let rawProperties = JSON.stringify(properties);
  
      let userInfo = await provarDxUtils.getDxUsersInfo(properties.connectionOverride);
      if(userInfo == null) {
        this.ux.error('[ERROR] No valid user org found to run tests. Terminating command.');
        return {};
      }
      let userInfoString = provarDxUtils.prepareRawProperties(JSON.stringify({'dxUsers': userInfo}));
      let updateProperties = provarDxUtils.prepareRawProperties(rawProperties);
      let jarPath = properties.provarHome +'/provardx/provardx.jar';
      execSync('java -cp "' + jarPath + '" com.provar.provardx.DxCommandExecuter ' + updateProperties + " " + userInfoString + " " + "Runtests", 
        {   stdio: 'inherit'});
      return {};
  }

  public updatePropertiesWithOverrides(properties: any, metadataLevel: string, cachePath: string, propertyFile: string) {
    properties.metadata.metadataLevel = metadataLevel == null ? properties.metadata.metadataLevel : metadataLevel;
    properties.metadata.cachePath = cachePath == null ? properties.metadata.cachePath: cachePath;
    properties.propertyFile = propertyFile == null ? properties.propertyFile: propertyFile;
    return properties;
  }
}
