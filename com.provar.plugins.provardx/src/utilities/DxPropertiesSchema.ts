/*
 * Copyright (c) 2020 Make Positive Provar Ltd
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE.md file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

export const schema = {
    $schema: 'http://json-schema.org/draft-07/schema#',
    $id: 'http://provartesting.com/provardx-props.schema.json',
    title: 'ProvarDX-Properties',
    description: 'A ProvarDX Property File',
    type: 'object',
    properties: {
        provarHome: {
            description:
                'Contains the location that the Provar installation was unzipped. ​This attribute is not required if there is a full Provar installation in the default location, or if PROVAR_HOME Environment Variable is set',
            type: 'string'
        },
        projectPath: {
            description:
                'The fully qualified path of the Test Project containing the tests to be run.  This is the folder that contains the .testproject file',
            type: 'string'
        },
        smtpPath: {
            description:
                'The fully qualified path of the .smtp folder to which Provar will use to send emails',
            type: 'string'
        },
        resultsPath: {
            description:
                'The fully qualified path of the folder to which Provar will write the test results',
            type: 'string'
        },
        resultsPathDisposition: {
            description: 'Controls the result folder disposition.',
            type: 'string',
            enum: ['Increment', 'Replace', 'Fail']
        },
        testOutputLevel: {
            description:
                'Controls the amount of test output logged to the DX test log.',
            type: 'string',
            enum: ['SEVERE', 'WARNING', 'INFO', 'FINE', 'FINER', 'FINEST']
        },
        pluginOutputlevel: {
            description:
                'Controls the amount of plugin output logged to the DX test log.',
            type: 'string',
            enum: ['SEVERE', 'WARNING', 'INFO', 'FINE', 'FINER', 'FINEST']
        },
        excludeCallable: {
            description:
                'Indicates whether Callable Test Cases should be omitted from execution (true) or included in execution (false)',
            type: 'boolean'
        },
        stopOnError: {
            description:
                'Indicates whether the Test Run should abort automatically if any test failure is encountered',
            type: 'boolean'
        },
        connectionRefreshType: {
            description: 'Controls the Metadata at Connection level',
            type: 'string'
        },
        lightningMode: {
            description: 'Indicates org is lightning or not',
            type: 'boolean'
        },
        metadata: {
            description: 'Controls the Salesforce Metadata usage.',
            type: 'object',
            properties: {
                metadataLevel: {
                    description: 'Controls the Salesforce Metadata usage',
                    type: 'string',
                    enum: ['Reuse', 'Reload', 'Refresh']
                },
                cachePath: {
                    description:
                        'The fully qualified path of the folder that will be used for storing the metadata cache',
                    type: 'string'
                }
            },
            required: ['metadataLevel', 'cachePath']
        },
        environment: {
            description: '',
            type: 'object',
            properties: {
                testEnvironment: {
                    description:
                        'The name of the Test Environment that the test run should be executed against. Test Environments are defined in Test Settings',
                    type: 'string'
                },
                webBrowser: {
                    description: 'The web browser to be used for UI testing',
                    type: 'string',
                    enum: [
                        'Chrome',
                        'Edge',
                        'Firefox',
                        'SAFARI',
                        'IE',
                        'Chrome_Headless'
                    ]
                },
                webBrowserConfig: {
                    description:
                        'The web browser window size. Browser configurations are defined in Test Settings',
                    type: 'string'
                },
                webBrowserProviderName: {
                    description: 'Web browser provider name.',
                    type: 'string'
                },
                webBrowserDeviceName: {
                    description: 'Web browser device name',
                    type: 'string'
                }
            }
        },
        testprojectSecrets: {
            description: 'Testproject secret encryption password.',
            type: 'string'
        },
        environmentsSecrets: {
            description: 'Test environment secret encryption password.',
            type: 'array',
            items: {
                type: 'object',
                properties: {
                    name: { type: 'string' },
                    secretsPassword: { type: 'string' }
                }
            }
        },
        testplanFeatures: {
            description: 'Test plan features.',
            type: 'array',
            items: {
                type: 'object',
                properties: {
                    name: { type: 'string' },
                    type: { type: 'string' },
                    enabled: { type: 'string' }
                }
            }
        },
        email: {
            description:
                'One or more email recipients defining settings for sending email upon test execution completion',
            type: 'object',
            properties: {
                sendEmail: {
                    description:
                        'Indicates whether an email should be sent after test run execution',
                    type: 'boolean'
                },
                toRecipients: {
                    description:
                        'The primary email addresses which should receive the email',
                    type: 'array',
                    items: {
                        type: 'string'
                    }
                },
                ccRecipients: {
                    description:
                        'he email addresses which should be CC’d in the email',
                    type: 'array',
                    items: {
                        type: 'string'
                    }
                },
                bccRecipients: {
                    description:
                        'The email addresses which should be BCC’d in the email',
                    type: 'array',
                    items: {
                        type: 'string'
                    }
                }
            },
            required: ['sendEmail', 'toRecipients']
        },
        testPlan: {
            description:
                'List of test plan names to be executed, wildcards are permitted',
            type: 'array',
            items: {
                type: 'string'
            }
        },
        testCase: {
            description:
                'One or more fileset elements defining the .testcase files to be run.  You can specify individual Test Case files, folders containing Test Case files or a combination of both',
            type: 'array',
            items: {
                type: 'string'
            }
        },
        connectionOverride: {
            description:
                'name:value pairs where the first entry is the provar Connection name and the second is a SFDX username/user-alias to substitute',
            type: 'array',
            items: {
                type: 'object',
                properties: {
                    connection: { type: 'string' },
                    username: { type: 'string' }
                }
            }
        }
    }
};
