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

import groovy.transform.CompileStatic
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import simplespringsecurity.config.script.inmemeory.InMemoryUserBuilder

/**
 * @author Jeff Brown
 * @since 1.0
 */
@CompileStatic
class UserBuilder {
    AuthenticationManagerBuilder managerBuilder

    UserBuilder(AuthenticationManagerBuilder managerBuilder) {
        this.managerBuilder = managerBuilder
    }

    void build(Closure closure) {
        Closure c = (Closure) closure.clone()
        c.delegate = this
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c()
    }

    void inMemory(Closure closure) {
        closure.delegate = new InMemoryUserBuilder(managerBuilder.inMemoryAuthentication())
        closure()
    }
}
