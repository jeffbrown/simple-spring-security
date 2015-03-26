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
package simplespringsecurity.config.script

import grails.core.GrailsApplication
import grails.core.support.GrailsApplicationAware
import groovy.transform.CompileStatic
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity

/**
 * @author Jeff Brown
 * @since 1.0
 */
@EnableWebMvcSecurity
@CompileStatic
class ScriptConfigUserManager implements GrailsApplicationAware, InitializingBean {

    GrailsApplication grailsApplication

    @Autowired
    AuthenticationManagerBuilder managerBuilder

    void afterPropertiesSet() {
        def resource = grailsApplication.mainContext.getResource("classpath:simpleSpringSecurity.groovy")
        if (resource?.exists()) {
            def gcl = new GroovyClassLoader(grailsApplication.classLoader)
            def c = gcl.parseClass(resource.inputStream, resource.filename)
            Script script = (Script) c.newInstance();
            script.run();
            Object users = script.getProperty("users")
            if (users instanceof Closure) {
                new UserBuilder(managerBuilder).build(users)
            }
        }
    }
}
