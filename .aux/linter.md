## .config/pmd/ruleset.xml

```xml
<?xml version="1.0"?>
<ruleset name="devflow"
         xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">
    <description>Reglas de calidad de Task CLI</description>

    <rule ref="category/java/bestpractices.xml">
        <exclude name="SystemPrintln"/>
        <exclude name="UnitTestContainsTooManyAsserts"/>
        <exclude name="UnitTestAssertionsShouldIncludeMessage"/>
    </rule>

    <rule ref="category/java/errorprone.xml">
        <exclude name="AvoidLiteralsInIfCondition"/>
    </rule>

    <rule ref="category/java/codestyle.xml/UnnecessaryImport"/>
    <rule ref="category/java/codestyle.xml/UselessParentheses"/>

    <rule ref="category/java/design.xml/SimplifyBooleanReturns"/>
    <rule ref="category/java/design.xml/CognitiveComplexity"/>
</ruleset>
```

## pom.xml

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.28.0</version>
    <dependencies>
        <dependency>
            <groupId>net.sourceforge.pmd</groupId>
            <artifactId>pmd-core</artifactId>
            <version>7.28.0</version>
        </dependency>
        <dependency>
            <groupId>net.sourceforge.pmd</groupId>
            <artifactId>pmd-java</artifactId>
            <version>7.28.0</version>
        </dependency>
    </dependencies>
    <configuration>
        <rulesets>
            <ruleset>.config/pmd/ruleset.xml</ruleset>
        </rulesets>
        <targetJdk>21</targetJdk>
        <includeTests>true</includeTests>
        <printFailingErrors>true</printFailingErrors>
    </configuration>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## scripts/lint.sh

```bash
#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

echo "==> Pasando el linter..."
mvn compile pmd:check
```
