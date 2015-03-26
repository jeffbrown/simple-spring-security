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
package simplespringsecurity.config.script.inmemeory

import groovy.transform.CompileStatic
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer

/**
 * @author Jeff Brown
 * @since 1.0
 */
@CompileStatic
class InMemoryUserBuilder {
    protected InMemoryUserDetailsManagerConfigurer configurer

    InMemoryUserBuilder(InMemoryUserDetailsManagerConfigurer configurer) {
        this.configurer = configurer
    }

    void build(Closure closure) {
        Closure c = (Closure)closure.clone()
        c.delegate = this
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c()
    }

    void newUser(Map attributes) {
        if (attributes.username instanceof CharSequence) {
            UserDetailsManagerConfigurer.UserDetailsBuilder builder = configurer.withUser(attributes.username.toString())
            if (attributes.password instanceof CharSequence) {
                builder.password((String)attributes.password.toString())
            }
            if (attributes.roles instanceof List) {
                List rolesList = (List)attributes.roles
                String[] rolesArray = rolesList.toArray(new String[rolesList.size()])
                builder.roles rolesArray
            }
        }
    }
}
