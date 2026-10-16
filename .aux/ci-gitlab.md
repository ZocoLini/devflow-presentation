## .gitlab-ci.yml

```yaml
stages:
  - check
  - test

.java-image:
  image: maven:3-eclipse-temurin-21
  variables:
    MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository"
  cache:
    paths:
      - .m2/repository

include:
  - local: .gitlab/workflows/**.yml
```

## .gitlab/workflows/format.yml

```yaml
format:
  extends: .java-image
  stage: check
  script:
    - ./scripts/format.sh --check
```

## .gitlab/workflows/lint.yml

```yaml
lint:
  extends: .java-image
  stage: check
  script:
    - ./scripts/lint.sh
```

## .gitlab/workflows/test.yml

```yaml
test:
  extends: .java-image
  stage: test
  needs: [format, lint]
  script:
    - ./scripts/test.sh
  artifacts:
    when: always
    reports:
      junit: target/surefire-reports/TEST-*.xml
```
