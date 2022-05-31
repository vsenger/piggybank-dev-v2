AWS_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
AWS_REGION=$(aws configure get region)
cdk bootstrap aws://${AWS_ACCOUNT}/${AWS_REGION}
