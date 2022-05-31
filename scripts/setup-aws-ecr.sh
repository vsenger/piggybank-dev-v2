aws ecr create-repository --repository-name caravanacloud/pbnk-api-lambda
docker tag [SOME SHA] 269733383066.dkr.ecr.ap-northeast-1.amazonaws.com/caravanacloud/pbnk-api-lambda
aws ecr get-login-password --region ap-northeast-1
aws ecr get-login-password --region ap-northeast-1 | docker login --username AWS --password-stdin 269733383066.dkr.ecr.ap-northeast-1.amazonaws.com
ROLE_ARN=arn:aws:iam::269733383066:role/pbnk-role


IMAGE_URI=269733383066.dkr.ecr.ap-northeast-1.amazonaws.com/caravanacloud/pbnk-api-lambda:latest

aws lambda create-function \
  --function-name pbnk-api-fn \
  --package-type Image \
  --code ImageUri=${IMAGE_URI} \
  --role $ROLE_ARN \
  --memory-size 10240 \
  --timeout 360 \
  --environment "Variables={QUARKUS_DATASOURCE_JDBC_URL=${QUARKUS_DATASOURCE_JDBC_URL},QUARKUS_DATASOURCE_PASSWORD=${QUARKUS_DATASOURCE_PASSWORD},QUARKUS_DATASOURCE_USERNAME=${QUARKUS_DATASOURCE_USERNAME}}"

# Config mem, timeout, env
curl https://s54gjivu5ckldkakqxu353al6a0limjj.lambda-url.ap-northeast-1.on.aws/api/hello
