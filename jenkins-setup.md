# Jenkins Setup Guide

This guide will help you set up Jenkins to run your automated tests.

## Prerequisites

1. Jenkins server installed and running
2. Maven plugin installed in Jenkins
3. JDK 17 configured in Jenkins
4. HTML Publisher plugin installed

## Jenkins Configuration

### 1. Configure Tools in Jenkins

Go to **Manage Jenkins** > **Tools**:

- Add a JDK installation named "JDK17" and configure its path
- Add a Maven installation named "Maven" and configure its path

### 2. Create a Jenkins Pipeline Job

1. From the Jenkins dashboard, click **New Item**
2. Enter a name for your job (e.g., "Automation-Tests")
3. Select **Pipeline** and click **OK**
4. In the configuration page:
   - Under **Pipeline**, select **Pipeline script from SCM**
   - Select **Git** as the SCM
   - Enter your repository URL
   - Specify the branch (e.g., */main)
   - Script Path: `Jenkinsfile`
5. Click **Save**

### 3. Running the Pipeline

1. Click **Build Now** to run the pipeline
2. The pipeline will:
   - Check out the code
   - Compile the project
   - Run tests in headless mode
   - Publish JUnit test results
   - Publish HTML Extent Reports
   - Archive screenshot artifacts

### 4. Viewing Test Results

After the pipeline runs:

1. Check the **Build Status** to see if the tests passed or failed
2. Click on **Extent Reports** link to view the test reports
3. Check **Test Results** for detailed test outcomes
4. Browse **Artifacts** to view the screenshots

## Advanced Configuration

### Scheduled Runs

To schedule the pipeline to run periodically:

1. Go to the job configuration
2. Check **Build Periodically** under **Build Triggers**
3. Enter a cron expression (e.g., `0 0 * * *` for daily at midnight)

### Email Notifications

To receive email notifications:

1. Configure email settings in Jenkins system configuration
2. Add email notifications to the Jenkinsfile post section:

```groovy
post {
    always {
        emailext (
            subject: "Test Results: ${currentBuild.fullDisplayName}",
            body: """Test Results Summary:
                    Build Status: ${currentBuild.result}
                    Check detailed results at: ${env.BUILD_URL}""",
            to: 'your.email@example.com'
        )
    }
}
``` 