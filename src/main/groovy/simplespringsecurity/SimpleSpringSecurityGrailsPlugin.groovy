/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simplespringsecurity

import grails.plugins.*
import simplespringsecurity.config.script.ScriptConfigAuthorizationManager
import simplespringsecurity.config.script.ScriptConfigUserManager

class SimpleSpringSecurityGrailsPlugin extends Plugin {

    def grailsVersion = "3.0.0.BUILD-SNAPSHOT > *"

    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Simple Spring Security"
    def author = "Jeff Brown"
    def authorEmail = "jeff@jeffandbetsy.net"
    def description = '''\
Grails 3 plugin for Spring Security.
'''
    def profiles = ['web']

    def documentation = "http://grails.org/plugin/simple-spring-security"

    def license = "APACHE"

    def issueManagement = [ system: "GitHub", url: "https://github.com/grails3-plugins/simple-spring-security/issues" ]

    def scm = [ url: "https://github.com/grails3-plugins/simple-spring-security/" ]

    @Override
    Closure doWithSpring() {
        { ->
            springSecurityAuthorizationManager ScriptConfigAuthorizationManager
            springSecurityUserManager ScriptConfigUserManager
        }
    }
}
