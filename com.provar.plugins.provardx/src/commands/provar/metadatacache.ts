import ProvarDXUtility from '../../utilities/ProvarDXUtility';
import { execSync } from 'child_process';
import { Messages } from '@salesforce/core';
import { SfdxCommand, flags } from '@salesforce/command';
import { AnyJson } from '@salesforce/ts-types';


/**
 * @description
 *The metadatacache ensures that the metadata cache is up to date prior to running Provar tasks. 
 *It can be executed in parallel with other commands as part of a pipeline script, 
 *but needs to complete before runtests is executed to avoid any duplication of metadata download.
 *If no user and no propertyfile is specified then the metadata will be downloaded for the SFDX current default user. 
 *This may not be what is expected or desired but is consistent with other DX commands! It is up to the user to correctly specify the user(s). 
 *For download metadata for multiple users itâ€™s recommended to reuse the propertyfile and override the metadata cache settings in the property file with the -m flag.

 * @author Himanshu Sharma
 */

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file.
const messages = Messages.loadMessages('@provartesting/provardx', 'metadatacache');
export default class metadatacache extends SfdxCommand {

  public static description = messages.getMessage('commandDescription');
  public static examples = [
    `$ sfdx provar:metadatacache -m 'refresh' -c './metadata' -f './myproperties.json'`
    ];
 
    
  protected static flagsConfig = {
    // flag with a value (-f, --propertyfile=VALUE)
    metadatalevel: flags.string({char: 'm', description: messages.getMessage('metadataLevelFlagDescription')}),
    // flag with a value (-c, --cachepath=VALUE)
    cachepath: flags.string({char: 'c', description: messages.getMessage('cachePathFlagDescription')}),
    // flag with a value (-p, --propertyfile=VALUE)
    propertyfile: flags.string({char: 'p', description: messages.getMessage('propertyFileFlagDescription')}),
    // flag with a value (-l, --loglevel VALUE)
    loglevel: flags.string({char: 'l', description: messages.getMessage('loglevelFlagDescription')}),
    // flag with a value (-n, --connections VALUE)
    connections: flags.string({char: 'n', description: messages.getMessage('connectionNameFlagDescription')}),
    // flag with a value (-o, --connectionoverrides VALUE)
    connectionoverrides: flags.string({char: 'o', description: messages.getMessage('connectionoverridesFlagDescription')})
  };

  
  public static args = [{name: 'file'}];

  public async run(): Promise<AnyJson> {
    const metadataLevel : string = this.flags.metadatalevel;
    const cachePath : string = this.flags.cachepath;
    const propertyFile : string = this.flags.propertyfile;
    //const json : string = this.flags.propertyFile;
    //const logLevel : string = this.flags.loglevel ? this.flags.loglevel : 'INFO';
    const connections : string = this.flags.connections;
    const connectionoverrides : string = this.flags.connectionoverrides;
    
    let provarDxUtils : ProvarDXUtility = new ProvarDXUtility();
    let isValid : boolean = provarDxUtils.validatePropertiesJson(propertyFile);
    let propertiesInstance = provarDxUtils.getProperties();

    if(connections) {
      propertiesInstance.connectionName = connections;
    }
    
    if(!isValid || provarDxUtils.hasDuplicateConnectionOverride(propertiesInstance)) {
        this.ux.error("Invalid property file. Run command sfdx provar:validate' to know the validation errors");
        return {};
    }
    
    let properties = this.updatePropertiesWithOverrides(propertiesInstance, metadataLevel, cachePath, propertyFile, connectionoverrides);
    let rawProperties = JSON.stringify(properties);

    let updateProperties = provarDxUtils.prepareRawProperties(rawProperties);
    
    let userInfo = await provarDxUtils.getDxUsersInfo(properties.connectionOverride);
    if(userInfo == null) {
      this.ux.error('[ERROR] No valid user org found to download metadata. Terminating command.');
      return {};
    }

    let userInfoString = provarDxUtils.prepareRawProperties(JSON.stringify({'dxUsers': userInfo}));
    let jarPath = properties.provarHome +'/provardx/provardx.jar';
    execSync('java -cp "' + jarPath + '" com.provar.provardx.DxCommandExecuter ' + updateProperties + " " + userInfoString + " " + "Metadata", 
      {stdio: 'inherit'});
    return {};
  }

  public updatePropertiesWithOverrides(properties: any, metadataLevel: string, cachePath: string, propertyFile: string,
    connectionOverrides: string) {
    properties.metadata.metadataLevel = metadataLevel == null ? properties.metadata.metadataLevel : metadataLevel;
    properties.metadata.cachePath = cachePath == null ? properties.metadata.cachePath: cachePath;
    properties.propertyFile = propertyFile == null ? properties.propertyFile: propertyFile;
    this.doConnectionOverrides(properties, connectionOverrides);
    return properties;
  }
  
  private doConnectionOverrides(properties: any, connectionOverride : string): void {
    if(!connectionOverride) {
      return;
    }
    let overrides = connectionOverride.split(",");
    let connOver = [];
    for(let i=0;i<overrides.length;i++) {
      let v = overrides[0].split(":");
      connOver.push ({'connection': v[0], 'username': v[1]});
    }
    properties.connectionOverride = connOver;
  }
}
