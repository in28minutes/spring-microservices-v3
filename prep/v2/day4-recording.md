```
kubectl get all
kubectl get componentstatuses
kubectl get pods --all-namespaces
kubectl get pods --all-namespaces
kubectl get pods --all-namespaces -l app=hello-world-rest-api
kubectl get services --all-namespaces
kubectl get services --all-namespaces --sort-by=.spec.type
kubectl get services --all-namespaces --sort-by=.metadata.name
kubectl cluster-info
kubectl cluster-info dump
kubectl top node
kubectl top pod
kubectl get services
kubectl get svc
kubectl get ev
kubectl get rs
kubectl get ns
kubectl get nodes
kubectl get no
kubectl get pods
kubectl get po
```

```
kubectl create deployment currency-exchange --image=rangakaranam/mmv2-currency-exchange-service:0.0.1-SNAPSHOT
kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000
kubectl get pods
kubectl get service

kubectl get deployment currency-exchange -o yaml > deployment.yaml
kubectl get service currency-exchange -o yaml > deployment.yaml
kubectl delete all -l app=currency-exchange
kubectl apply -f deployment.yaml
kubectl diff -f deployment.yaml
```

```
      - image: rangakaranam/mmv2-currency-conversion-service:0.0.3-SNAPSHOT
        imagePullPolicy: IfNotPresent
        name: mmv2-currency-conversion-service
        envFrom:
          - configMapRef:
              name: currency-conversion
#        env:
#          - name: CURRENCY_EXCHANGE_URI
#            value: http://currency-exchange
```

```
kubectl get service --watch
kubectl logs -f currency-exchange-5f9b84cfd8-t4c76
```

```
kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
kubectl get configmaps
kubectl apply -f ../currency-exchange-service/deployment.yaml
```

Enable Logging APIs

Blue Green deployments
- Two Deployments and one Service

Rollout and backout
```
<!--SAFETY-->
kubectl rollout history deployment currency-exchange
kubectl set image deployment currency-exchange mmv2-currency-exchange-service=DUMMY
kubectl rollout undo deployment currency-exchange --to-revision=7
```

More safety through readinessProbe and livenessProbe
```
  spec:
    containers:
    - image: rangakaranam/mmv2-currency-exchange-service:0.0.5-SNAPSHOT
      imagePullPolicy: IfNotPresent
      name: mmv2-currency-exchange-service
      readinessProbe:
        httpGet:
          port: 8000
          path: /actuator/health/readiness
      livenessProbe:
        httpGet:
          port: 8000
          path: /actuator/health/liveness

```

Auto scaling
```
kubectl scale deployment currency-exchange --replicas=1
kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=10 
kubectl get hpa
kubectl top node
kubectl top pod
```

```
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
```


```
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: currency-exchange
  name: currency-exchange
spec:
  replicas: 1
  selector:
    matchLabels:
      app: currency-exchange
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: currency-exchange
    spec:
      containers:
      - image: rangakaranam/mmv2-currency-exchange-service:0.0.5-SNAPSHOT
        imagePullPolicy: IfNotPresent
        name: mmv2-currency-exchange-service
        readinessProbe:
          httpGet:
            port: 8000
            path: /actuator/health/readiness
        livenessProbe:
          httpGet:
            port: 8000
            path: /actuator/health/liveness
      restartPolicy: Always
      terminationGracePeriodSeconds: 30
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: currency-exchange
  name: currency-exchange
  namespace: default
spec:
  ports:
  - port: 8000
    protocol: TCP
    targetPort: 8000
  selector:
    app: currency-exchange
  sessionAffinity: None
  type: LoadBalancer
```
