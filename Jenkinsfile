node {
	// 拉取代码
	stage('Git Checkout') {
		checkout([$class: 'GitSCM', branches: [[name: '${branch}']], doGenerateSubmoduleConfigurations: false,
		extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId:'adf9f1d0-a09d-42e6-aa2c-c6a5dacc8ebc', url: 'http://212.129.149.40/181250043_owo/backend-owo.git']]])
	}
	// 代码编译
	stage('Maven Build') {
		sh '''
		export JAVA_HOME=/usr/local/jdk
		/usr/local/maven/bin/mvn clean package -Dmaven.test.skip=true
		'''
	}
	// 项目打包到镜像并推送到镜像仓库
	stage('Build and Push Image') {
		sh '''
		REPOSITORY=212.129.149.40/181250043_owo/backend-owo:${branch}
		cat > Dockerfile << EOF
		FROM 212.129.19.40/ceshi/tomcat:v1
		MAINTAINER wfy
		RUN rm -rf /usr/local/tomcat/webapps/
		ADD target/.war /usr/local/tomcat/webapps/ROOT.war
		EOF
		docker build -t $REPOSITORY .
		docker login 212.219.149.40 -u 181250043 -p 181250043
		docker push $REPOSITORY
		'''
	}
	// 部署到Docker主机
	stage('Deploy to Docker') {
		sh '''
		REPOSITORY=212.129.149.40/181250043_owo/backend-owo:${branch}
		docker rm -f tomcat-java-demo |true
		docker pull $REPOSITORY
		docker container run -d --name tomcat-java-demo -p 88:8080 $REPOSITORY
		'''
	}
}