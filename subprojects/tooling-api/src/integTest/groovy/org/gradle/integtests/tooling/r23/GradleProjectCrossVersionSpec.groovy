/*
 * Copyright 2014 the original author or authors.
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


package org.gradle.integtests.tooling.r23

import org.gradle.integtests.tooling.fixture.TargetGradleVersion
import org.gradle.integtests.tooling.fixture.ToolingApiSpecification
import org.gradle.integtests.tooling.fixture.ToolingApiVersion
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.model.GradleProject

class GradleProjectCrossVersionSpec extends ToolingApiSpecification {

    @ToolingApiVersion(">=2.3")
    @TargetGradleVersion(">=2.3")
    def "populate isPublic field on tasks from GradleProject"() {
        file("build.gradle") << """
task publicTask {
  group = 'myGroup'
}
task privateTask {
}
"""

        when:
        def gradleProject = withConnection { ProjectConnection connection ->
            connection.getModel(GradleProject.class)
        }

        then:
        gradleProject != null
        gradleProject.tasks.find { it.name.equals('publicTask') }.isPublic()
        !gradleProject.tasks.find { it.name.equals('privateTask') }.isPublic()
    }

}
