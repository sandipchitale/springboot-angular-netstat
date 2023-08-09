# A simple Spring Boot application with Angular frontend and PrimeNG components

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Angular](https://angular.io/)
* [PrimeNG](https://primeng.org)

Built using Gradle

* [Gradle](https://gradle.org/)


# Key Implementation Details

## Gradle

### Node plugin

```groovy
	id 'com.github.node-gradle.node' version '5.0.0'
```

### Configure Node Plugin

```groovy
node {
    download = true
    version = '18.17.0'
    npmVersion = ''

    distBaseUrl = 'https://nodejs.org/dist'

    setWorkDir(file("${project.projectDir}/.gradle/nodejs"))
    setNpmWorkDir(file("${project.projectDir}/.gradle/npm"))
    setNodeProjectDir(file("${project.projectDir}/src/main/angularapp")) // the Angular app source
}
```

### Tasks

```groovy
tasks.register('gradleNgBuild', NpmTask) {
	group 'frontend-build'
	description 'Builds the Angular app using ng build'

	dependsOn npmInstall
	args = ['run', 'gradle-build'] // gradle-build is a script in package.json

	inputs.dir file("${project.projectDir}/src/main/angularapp") // the Angular app source
	outputs.dir file("${project.projectDir}/build/resources/main/static") // the Angular app output
}

tasks.named('classes').configure {
	dependsOn 'gradleNgBuild' // run the Angular build alongside the Java related tasks
}
```

## Angular

Generated Angular app using `ng new angularapp`.

### package.json

#### Build Script
```jsonc
  "scripts": {
    // ...
    "gradle-build": "ng build --output-path=../../../build/resources/main/static", // output the Angular build to the Spring Boot static folder
    // ...
  },
```

#### Primeng dependencies

```jsonc
  "dependencies": {
    // ...
    "primeflex": "^3.3.1",
    "primeicons": "^6.0.1",
    "primeng": "^16.0.2",
    // ...
  },
```

### angular.json

#### favicon

```jsonc
        "assets": [
            // ...
          "src/favicon-32x32.png",
            // ...
        ],
```

#### styles and theme switcher

```jsonc
        "styles": [
          "src/styles.scss",
          "node_modules/primeicons/primeicons.css", // add PrimeNG icons
          {
            "input": "src/light-theme.scss", // light themes
            "bundleName": "light-theme",
            "inject": false
          },
          {
            "input": "src/dark-theme.scss", // dark themes
            "bundleName": "dark-theme",
            "inject": false
          },
          "src/vscode-primeng.scss",
          "node_modules/primeng/resources/primeng.min.css",
          "node_modules/primeflex/primeflex.css"
        ],
```

#### light-theme.scss

```scss
@import "primeng/resources/themes/saga-blue/theme.css";
```

#### dark-theme.scss

```scss
@import "primeng/resources/themes/arya-blue/theme.css";
```

### index.html

```html
<!-- ... -->
  <base href="./"> <!-- make angular App contextPath independenct -->
  <link rel="icon" href="favicon-32x32.png" type="image/png"> <!-- favicon -->
  <link id="app-theme" rel="stylesheet" type="text/css" href="light-theme.css"> <!-- theme switcher -->
<!-- ... -->
```

### app.component.ts

```typescript
  ngAfterViewInit(): void {
    this.adjustTheme();
  }

  adjustTheme(event?: any) {
    let theme = 'light-theme';
    if (this.lightTheme) {
      theme = 'light-theme';
    } else {
      theme = 'dark-theme';
    }
    const themeLink = this.document.getElementById('app-theme') as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
    }
  }
```