/*
 * Copyright (c) 2020 Make Positive Provar Ltd
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE.md file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

import * as fs from 'fs';
import { schema } from './DxPropertiesSchema';

import { AnyJson } from '@salesforce/ts-types';
import { cli } from 'cli-ux';
import { Validator, ValidatorResult } from 'jsonschema';
/**
 * Utility class for provar dx commands.
 * @author Himanshu Sharma
 */
export default class ProvarDXUtility {
    public provarDxPropertiesJsonLoc: string = './provardx-properties.json';
    public validationResults: ValidatorResult;
    public propertyInstance: any; // tslint:disable-line:no-any

    /**
     * Validate the dx properties json file.
     * @param propertyJson
     */
    public validatePropertiesJson(propertyJson: string): boolean {
        const jsonValidator = new Validator();
        const propertiesLoc = propertyJson
            ? propertyJson
            : this.provarDxPropertiesJsonLoc;
        const instance = JSON.parse(fs.readFileSync(propertiesLoc).toString());
        this.propertyInstance = instance;

        this.validationResults = jsonValidator.validate(instance, schema);

        if (this.validationResults.errors.length > 0) {
            return false;
        }
        return true;
    }

    /**
     * Check for duplicate connection override properties.
     * @param instance
     */
    // tslint:disable-next-line: ban-types
    public hasDuplicateConnectionOverride(instance: Object): boolean {
        const overrideMap = new Map();
        const override = instance['connectionOverride'];
        if (override === undefined) {
            return false;
        }
        for (const o of override) {
            const connectionName = o.connection;
            if (overrideMap.has(connectionName)) {
                return true;
            }
            overrideMap.set(connectionName, o.username);
        }
        return false;
    }

    /**
     * Returns the validation results
     */
    public getValidationResults(): ValidatorResult {
        return this.validationResults;
    }

    /**
     * Returns the validated dx properties instance
     */
    // tslint:disable-next-line:no-any
    public getProperties(): any {
        return this.propertyInstance;
    }

    /**
     * Updates the dx properties json string before it is send to command executer.
     * @param rawProperties
     */
    public prepareRawProperties(rawProperties: string): string {
        return '"' + rawProperties.replace(/"/g, '\\"') + '"';
    }

    /**
     * Gets the dx user info and generated the password for dx user if not already created.
     * @param overrides Connection overrides provided in dx property file.
     */
    public async getDxUsersInfo(overrides: string): Promise<AnyJson> {
        const dxUsers = [];
        if (overrides === undefined) {
            return dxUsers;
        }
        for (const override of overrides) {
            const username = override['username'];
            const message =
                'Validating and retriving dx user info: ' + username;
            let dxUserInfo = await this.executeCommand(
                'sfdx force:user:display --json -u ' + username,
                message
            );
            let jsonDxUser = JSON.parse(dxUserInfo.toString());
            if (jsonDxUser.status !== 0) {
                console.error(
                    '[WARNING] ' + jsonDxUser.message + '. Skipping operation.'
                );
                continue;
            }
            if (jsonDxUser.result.password == null) {
                const generatePasswordCommand =
                    'sfdx force:user:password:generate --targetusername ' +
                    username;
                await this.executeCommand(
                    generatePasswordCommand,
                    'Generating password for user: ' + username
                );
                dxUserInfo = await this.executeCommand(
                    'sfdx force:user:display --json -u ' + username,
                    'Getting generated password for user: ' + username
                );
                jsonDxUser = JSON.parse(dxUserInfo.toString());
            }
            jsonDxUser.result.connection = override['connection'];
            jsonDxUser.result.password = this.handleSpecialCharacters(
                jsonDxUser.result.password
            );
            dxUsers.push(jsonDxUser);
        }
        if (dxUsers.length === 0) {
            return null;
        }
        return dxUsers;
    }

    /**
     * Executes the provided dx command.
     * @param command Command string
     * @param message Message to be displayed while command execution is in progress.
     */
    private async executeCommand(
        command: string,
        message: string
    ): Promise<AnyJson> {
        if (message) {
            cli.action.start(message);
        }
        let isSucessful = false;
        const { promisify } = require('util');
        const exec = promisify(require('child_process').exec);
        try {
            const result = await exec(command);
            isSucessful = true;
            return result.stdout;
        } catch (e) {
            let errorMessage = e.message;
            errorMessage = errorMessage.substring(
                errorMessage.indexOf('{'),
                errorMessage.indexOf('}') + 1
            );
            return errorMessage;
        } finally {
            if (message) {
                cli.action.stop(isSucessful ? 'successful' : 'failed');
            }
        }
    }

    private handleSpecialCharacters(password: string): string {
        if (password) {
            password = encodeURIComponent(password);
        }
        return password;
    }
}
