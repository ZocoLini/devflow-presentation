## .config/spotless/eclipse-formatter.xml

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<profiles version="23">
    <profile kind="CodeFormatterProfile" name="devflow" version="23">
        <setting id="org.eclipse.jdt.core.formatter.tabulation.char" value="space"/>
        <setting id="org.eclipse.jdt.core.formatter.tabulation.size" value="4"/>
        <setting id="org.eclipse.jdt.core.formatter.indentation.size" value="4"/>
        <setting id="org.eclipse.jdt.core.formatter.lineSplit" value="120"/>
        <setting id="org.eclipse.jdt.core.formatter.comment.line_length" value="120"/>
        <setting id="org.eclipse.jdt.core.formatter.join_wrapped_lines" value="false"/>
        <setting id="org.eclipse.jdt.core.formatter.continuation_indentation" value="2"/>
        <setting id="org.eclipse.jdt.core.formatter.blank_lines_before_first_class_body_declaration" value="0"/>
    </profile>
</profiles>
```

## pom.xml

```xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>3.10.4</version>
    <configuration>
        <java>
            <eclipse>
                <file>.config/spotless/eclipse-formatter.xml</file>
            </eclipse>
            <removeUnusedImports/>
            <importOrder/>
        </java>
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

## scripts/format.sh

```bash
#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

if [ "${1:-}" = "--check" ]; then
    echo "==> Comprobando formato..."
    mvn -q spotless:check
else
    echo "==> Formateando..."
    mvn -q spotless:apply
fi
```
