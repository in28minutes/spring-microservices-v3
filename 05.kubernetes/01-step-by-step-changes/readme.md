## Step 00 

- Step 00 - Docker, Kubernetes and Microservices - Made for each other
- Step 01 - Getting Started with Docker, Kubernetes and Google Kubernetes Engine
- Step 02 - Creating Google Cloud Account
- Step 03 - Creating Kubernetes Cluster with Google Kubernete Engine (GKE)
- Step 04 - Review Kubernetes Cluster and Learn Few Fun Facts about Kubernetes

## Step 05 

- Step 05 - Deploy Your First Spring Boot Application to Kubernetes Cluster

```
docker run -p 8080:8080 in28min/hello-world-rest-api:0.0.1.RELEASE
kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
```

## Step 06

- Step 06 - Quick Look at Kubernetes Concepts - Pods, Replica Sets and Deployment

```
kubectl get pods
kubectl get replicaset
kubectl get deployment
kubectl get service

kubectl scale deployment hello-world-rest-api --replicas=3

```

## Step 07
- Step 07 - Understanding Pods in Kubernetes

```
kubectl get pods -o wide
kubectl explain pods
kubectl describe pod hello-world-rest-api-58ff5dd898-9trh2

```

## Step 08

- Step 08 - Understanding ReplicaSets in Kubernetes

```
kubectl get replicasets
kubectl get replicaset
kubectl get rs
kubectl delete pod hello-world-rest-api-58ff5dd898-62l9d

#Tell RS to have more pods
kubectl scale deployment hello-world-rest-api --replicas=3
kubectl get pods
kubectl get replicaset

kubectl get events
kubectl get events --sort.by=.metadata.creationTimestamp

kubectl explain replicaset
```

## Step 09
- Step 09 - Understanding Deployment in Kubernetes
- Step 10 - Quick Review of Kubernetes Concepts - Pods, Replica Sets and Deployment

```
kubectl get rs
kubectl get rs -o wide

# Deploy an ERROR IMAGE
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST

# However the application is still up and running
kubectl get rs -o wide # 2 replica sets
kubectl get pods
kubectl describe pod hello-world-rest-api-85995ddd5c-msjsm
kubectl get events --sort-by=.metadata.creationTimestamp


kubectl set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE

kubectl get events --sort-by=.metadata.creationTimestamp

```

## Step 11
- Step 11 - Understanding Services in Kubernetes
- Step 12 - Quick Review of GKE on Google Cloud Console
- Step 13 - Understanding Kubernetes Architecture - Master Node and Nodes

```
## Each pod has a different ip address
kubectl delete pod hello-world-rest-api-67c79fd44f-n6c7l
kubectl get pods -o wide
kubectl delete pod hello-world-rest-api-67c79fd44f-8bhdt

## kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080

## Review Google Cloud Loadbalancer in Google Cloud Console
kubectl get services
```

## Step 14

- Installing Gcloud
- Installing Kubectl

```
gcloud container clusters get-credentials in28minutes-cluster --zone us-central1-c --project solid-course-258105
```

- Step 14 - Setup Currency Exchange & Currency Conversion Microservices - K8S versions
- Step 15 - Create Container images for Currency Exchange & Currency Conversion Microservices
```
docker login
docker push in28min/mmv3-currency-exchange-service:0.0.11-SNAPSHOT
docker push in28min/mmv3-currency-conversion-service:0.0.11-SNAPSHOT
```
## Step 16

- Step 16 - Deploy Microservices to Kubernetes & Understand Service Discovery

URLs
- Currency Exchange Service - http://IP_ADDRESS:8000/currency-exchange/from/USD/to/INR
- Currency Conversion Service - http://IP_ADDRESS:8100/currency-conversion-feign/from/USD/to/INR/quantity/10


```
kubectl create deployment currency-exchange --image=in28min/mmv3-currency-exchange-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000
kubectl get svc
kubectl get services
kubectl get pods
kubectl get po
kubectl get replicaset
kubectl get rs
kubectl get all

kubectl create deployment currency-conversion --image=in28min/mmv3-currency-conversion-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100
kubectl get svc --watch

```


## Step 17
Step 17 - Creating Declarative Configuration Kubernetes YAML for Microservices

```
kubectl get deployments
 
kubectl get deployment currency-exchange -o yaml >> deployment.yaml 
kubectl get service currency-exchange -o yaml >> service.yaml 

# Create one file for deployment and service
 
kubectl diff -f deployment.yaml
kubectl apply -f deployment.yaml
```

## Step 18

Step 18 - Clean up Kubernetes YAML for Microservices

## Step 19

Step 19 - Enable Logging and Tracing APIs in Google Cloud Platform

- Enable Logging, Tracing and other Stack Driver APIs

## Step 20

- Step 20 - Deploying Microservices using Kubernetes YAML Configuration

```
kubectl delete all -l app=currency-exchange
kubectl delete all -l app=currency-conversion
kubectl apply -f deployment.yaml
```

## Step 21

- Step 21 - Playing with Kubernetes Declarative YAML Configuration
- Step 22 - Creating Environment Variables to enable Microservice Communication
- Step 23 - Understanding Centralized Configuration in Kubernetes - Config Maps
- Step 24 - Exploring Centralized Logging and Monitoring in GKE

```
kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
kubectl get configmap
 
kubectl get configmap currency-conversion -o yaml >> configmap.yaml
 
watch -n 0.1 curl http://34.66.241.150:8100/currency-conversion-feign/from/USD/to/INR/quantity/10
 
docker push in28min/mmv3-currency-conversion-service:0.0.12-SNAPSHOT
docker push in28min/mmv3-currency-exchange-service:0.0.12-SNAPSHOT
```

## Step 25

- Step 25 - Exploring Microservices Deployments with Kubernetes

```
kubectl rollout history deployment currency-conversion
kubectl rollout history deployment currency-exchange
kubectl rollout undo deployment currency-exchange --to-revision=1
 
kubectl logs currency-exchange-9fc6f979b-2gmn8
kubectl logs -f currency-exchange-9fc6f979b-2gmn8 
```

## Step 26

- Step 26 - Configuring Liveness and Readiness Probes for Microservices with K8S

Actuator URLs
- http://localhost:8000/actuator/health/
- http://localhost:8000/actuator/health/liveness
- http://localhost:8000/actuator/health/readiness

```
    spec:
      containers:
      - image: IMAGE
        imagePullPolicy: IfNotPresent
        name: NAME
        readinessProbe:
          httpGet:
            port: 8000
            path: /actuator/health/readiness
        livenessProbe:
          httpGet:
            port: 8000
            path: /actuator/health/liveness
      restartPolicy: Always
```

## Step 27
- Step 27 - Autoscaling Microservices with Kubernetes
- Step 28 - Delete Kubernetes Cluster and Thank You!

```
kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=5 
kubectl get hpa
 
kubectl top pod
kubectl top nodes
kubectl get hpa
kubectl delete hpa currency-exchange
```

