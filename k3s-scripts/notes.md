# K3S learning

## deploy

```shell
./run-k3s-server.sh
```

deploy redis ( yaml tranformed by kompose )

```shell
kubectl apply -f redis-mono-*.yaml
```

deploy application

```shell
kubectl apply -f deployment-public-api.yaml
```

## build package

for spring boot project, run this mvn goal will package an application image to your local

```
mvn spring-boot:build-image -DactivatedProperties=prod
```

run local docker image registry for dev

```shell
./docker-run-local-registry.sh
```

tag your local image with registry namspace

```shell
docker tag play-public-api:0.0.1-SNAPSHOT localhost:5000/play-public-api
```

push to local registry

```shell
docker push localhost:5000/play-public-api
```

then you can use kubectl to create a deployment yaml for your image

```shell
kubectl create deployment play-public-api --image=timyangjava/play-public-api --dry-run -o=yaml > deployment-public-api.yaml
```


## Local deployment

In real world development, images pulled by `kubectl` deployment come from remote registry like [hub.docker.com](hub.docker.com), or GCP, AWS, etc. which requires remote account.

For local development, a local image registry will be a faster way to deploy.


* [run a local docker registry](https://docs.docker.com/registry/deploying/)
* [config k3s private registry](https://rancher.com/docs/k3s/latest/en/installation/private-registry/)

push docker image to local registry:

```shell
docker push localhost:5000/myimage
```

## Migration from docker-compose

[Kompose - translate docker-compose to k8s yaml](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/)


## In progress

* How to access service through hostname url?
    - How DNS works in k8s?
