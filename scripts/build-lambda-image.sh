#!/bin/bash
set -ex

DOCKER_USER="caravanacloud"
DOCKER_IMAGE="pbnk-api-lambda"
DOCKER_VERSION="latest"
#DOCKER_TAG="${DOCKER_USER}/${DOCKER_IMAGE}:${DOCKER_VERSION}"
AWS_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
AWS_REGION=$(aws configure get region)
DOCKER_TAG="${AWS_ACCOUNT}.dkr.ecr.${AWS_REGION}.amazonaws.com/${DOCKER_USER}/${DOCKER_IMAGE}:${DOCKER_VERSION}"

echo "${DOCKER_TAG}"

docker build \
  -f pbnk-api/Dockerfile.lambda \
  --no-cache \
  --progress=plain \
  pbnk-api \
  -t "$DOCKER_TAG"

docker push "$DOCKER_TAG"

echo "Built and pushed ${DOCKER_TAG}"