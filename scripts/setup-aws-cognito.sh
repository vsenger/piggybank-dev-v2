#!/bin/false #Run those manually :)

# Create User Pool
POOL_NAME=piggybank-pool
aws cognito-idp create-user-pool --pool-name $POOL_NAME

# Check
aws cognito-idp list-user-pools --max-results 20
POOL_ID=ap-northeast-1_4hJ1sra1Y

# Create Resource Server
aws cognito-idp create-resource-server \
  --name piggybank-service \
  --identifier piggybank \
  --user-pool-id ${POOL_ID} \
  --scopes ScopeName=pbnk-user-scope,ScopeDescription=pbnk-user-scope-description

# Create Client
aws cognito-idp create-user-pool-client \
  --user-pool-id ${POOL_ID} \
  --allowed-o-auth-flows client_credentials \
  --client-name piggybank-client \
  --generate-secret \
  --allowed-o-auth-scopes piggybank/pbnk-user-scope \
  --allowed-o-auth-flows-user-pool-client

CLIENT_ID=2eh6qcm3e9phla8cfjoaf1012o

# Create Pool Domain
aws cognito-idp create-user-pool-domain \
  --domain piggybank-domain \
  --user-pool-id ${POOL_ID}

https://piggybank-domain.auth.ap-northeast-1.amazoncognito.com/oauth2/token