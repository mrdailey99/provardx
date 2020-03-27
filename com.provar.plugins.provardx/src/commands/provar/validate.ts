import { flags, SfdxCommand } from '@salesforce/command';
import { AnyJson } from '@salesforce/ts-types';
import { Messages } from '@salesforce/core';
import ProvarDXUtility from '../../utilities/ProvarDXUtility';
import { ValidatorResult } from 'jsonschema';


/**
 * This command will be used to check if provided properties json is valid or not.
 * Validation will be done against standard provardx property json schema.
 * @description
 * Command syntax: sfdx provar:metadatacache 
 * [-f | --propertyfile] Optional. Specify the location of custom provardx property file.
 * [--json] Optional. Show validation results in json format.
 * [--loglevel] Optional. Avalidable log level [INFO|SEVERE|WARNING|FINE|FINER|FINEST]
 * @author Himanshu Sharma
 * 
 */

// Initialize Messages with the current plugin directory
Messages.importMessagesDirectory(__dirname);

// Load the specific messages for this file.
const messages = Messages.loadMessages('@provartesting/provardx', 'validate');

export default class validate extends SfdxCommand {

  public static description = messages.getMessage('commandDescription');
  public static examples = [
    `$ sfdx provar:validate
    Property file is valid.
    `,
    `$ sfdx provar:validate --json
    Invalid property file.
    [Detailed validation error shown]
    `,
    `$ sfdx provar:validate -p './provardx-properties.json' --json
    Invalid property file.
    [Detailed validation error shown]
    `
    ];

  protected static flagsConfig = {
    // flag with a value (-p, --propertyfile=VALUE)
    propertyfile: flags.string({char: 'p', description: messages.getMessage('propertyFileFlagDescription')}),
    // flag with a value (-l, --loglevel VALUE)
    loglevel: flags.string({char: 'l', description: messages.getMessage('loglevelFlagDescription')})
  };
  
  public static args = [{name: 'file'}];
  
  public async run(): Promise<AnyJson> {
    const propertyFile : string = this.flags.propertyfile;
    const json : boolean = this.flags.json;
    const loglevel : string = this.flags.loglevel ? this.flags.loglevel : 'INFO';
  
    let provarDxUtils : ProvarDXUtility = new ProvarDXUtility();
    let isValid : boolean = provarDxUtils.validatePropertiesJson(propertyFile);
    let results : ValidatorResult = provarDxUtils.getValidationResults();

    let  errorMsgs= {'message': ''};

    if(provarDxUtils.hasDuplicateConnectionOverride(provarDxUtils.getProperties())) {
      errorMsgs.message = "Duplicate connection overrides, you can't have multiple connection overrides for same connection.";
      isValid = false;
    }
   
    if(isValid) {
      if(loglevel == 'INFO' || loglevel == 'FINE' || loglevel == 'FINNER' || loglevel == 'FINEST' ) {
        if(json) {
          this.ux.logJson({'isValid': true, 'properties' : results.instance});
        } else {
          this.ux.log('Property file is valid.');
        }
      } else if(loglevel == 'SEVERE' || loglevel == 'WARNING') {
        if(json) {
          this.ux.logJson( {'isValid': true});
        } else {
          this.ux.log('Property file is valid.');
        }
      }
    } else {
      for(let i = 0; i < results.errors.length; i++) {
        errorMsgs['message'] += results.errors[i].property + " " + results.errors[i].message + ", ";
      }
      if(loglevel === 'INFO' || loglevel === 'FINE' || loglevel === 'FINNER' || loglevel === 'FINEST' ) {
        if(json) {
          this.ux.logJson( {'isValid': false});
          this.ux.logJson (results.errors);
        } else {
          this.ux.error('Property file is not valid.');
          this.ux.error(errorMsgs.message);
        }
      } else if(loglevel === 'SEVERE' || loglevel === 'WARNING') {
        if(json) {
          this.ux.logJson( {'isValid': false, 'message' : errorMsgs.message});
        } else {
          this.ux.error('Property file is not valid.');
          this.ux.error(errorMsgs.message);
        }
      }
    }
    return {};
  }
}
