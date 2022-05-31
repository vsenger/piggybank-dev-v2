curl -X POST \
  ${COGNITO_URL}/oauth2/token \
  -H "authorization: Basic ${COGNITO_CLIENT_TOKEN}" \
  -H "content-type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=resources.piggybank%2Fresources"