/*
 * Copyright (c) 2020 Make Positive Provar Ltd
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE.md file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

import { flags, SfdxCommand } from '@salesforce/command';
import { Messages } from '@salesforce/core';
import { AnyJson } from '@salesforce/ts-types';
import { execSync } from 'child_process';
import ProvarDXUtility from '../../utilities/ProvarDXUtility';

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
export default class Compile extends SfdxCommand {
    public static description = messages.getMessage('commandDescription');
    public static examples = [
        "$ sfdx provar:compile -p './myproperties.json' --json --loglevel SEVERE"
    ];

    public static args = [{ name: 'file' }];

    protected static flagsConfig = {
        propertyfile: flags.string({
            char: 'p',
            description: messages.getMessage('propertyFileFlagDescription')
        }),
        provarhome: flags.string({
            char: 'h',
            description: messages.getMessage('provarHomeFlagDescription')
        }),
        projectpath: flags.string({
            char: 'c',
            description: messages.getMessage('projectPathFlagDescription')
        }),
        loglevel: flags.string({
            char: 'l',
            description: messages.getMessage('loglevelFlagDescription')
        })
    };

    public async run(): Promise<AnyJson> {
        const propertyFile: string = this.flags.propertyfile;
        const json: boolean = this.flags.json;
        const loglevel: string = this.flags.loglevel
            ? this.flags.loglevel
            : 'INFO';
        const provarHome: string = this.flags.provarhome;
        const projectPath: string = this.flags.projectpath;

        const provarDxUtils: ProvarDXUtility = new ProvarDXUtility();
        const isValid: boolean = provarDxUtils.validatePropertiesJson(
            propertyFile
        );
        const jsonValue = json ? json : false;

        if (!isValid) {
            this.ux.error(
                "Invalid property file. Run command sfdx provar:validate -e true' to get the validation errors"
            );
            return {};
        }

        this.ux.log('Provided property file:' + propertyFile);
        this.ux.log('Json: ' + jsonValue);
        this.ux.log('LogLevel: ' + loglevel);

        const properties = this.updatePropertiesWithOverrides(
            provarDxUtils.getProperties(),
            provarHome,
            projectPath
        );
        const rawProperties = JSON.stringify(properties);

        if (properties.provarHome == null) {
            this.ux.error('Prover home is not specified.');
            return {};
        }

        const updateProperties = provarDxUtils.prepareRawProperties(
            rawProperties
        );
        const jarPath = properties.provarHome + '/provardx/provardx.jar';
        execSync(
            'java -cp "' +
                jarPath +
                '" com.provar.provardx.DxCommandExecuter ' +
                updateProperties +
                ' ' +
                'NA' +
                ' ' +
                'Compile',
            { stdio: 'inherit' }
        );

        return {};
    }

    public updatePropertiesWithOverrides(
        // tslint:disable-next-line: no-any
        properties: any,
        provarHome: string,
        projectPath: string
    ) {
        properties.provarHome =
            provarHome == null ? properties.provarHome : provarHome;
        properties.projectPath =
            projectPath == null ? properties.projectPath : projectPath;
        return properties;
    }
}
