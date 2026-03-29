# Observability Setup: OpenTelemetry with Zipkin

This document describes the changes made to integrate distributed tracing into the microservices architecture across **api-gateway**, **currency-exchange**, and **currency-conversion** projects.

---

## Overview

The following observability stack was added:

| Component | Purpose                                                                    |
|---|----------------------------------------------------------------------------|
| **OpenTelemetry** | Observability Framework, distributed tracing and telemetry instrumentation |
| **Zipkin** | Trace visualization                                                        |
| **OTel Collector** | Telemetry pipeline (receives, processes, exports)                          |

---

## Step 1 — Maven Dependencies (`pom.xml`)

Added the following dependencies to `pom.xml` in all three services: **api-gateway**, **currency-exchange**, and **currency-conversion**.

```xml
<!-- OpenTelemetry auto-instrumentation (Spring Boot 4) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-opentelemetry</artifactId>
</dependency>

<!-- OpenTelemetry Logback Appender for log forwarding -->
<dependency>
    <groupId>io.opentelemetry.instrumentation</groupId>
    <artifactId>opentelemetry-logback-appender-1.0</artifactId>
    <version>2.25.0-alpha</version>
</dependency>

```

**Notes:**
- `spring-boot-starter-opentelemetry` enables auto-instrumentation for traces and metrics.
- `opentelemetry-logback-appender-1.0` bridges Logback logs into the OTel pipeline.

---

[!Note]
- Remove the following dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-micrometer-tracing-brave</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-zipkin</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
```
## Step 2 — Add the following entries in the `application.properties` in all three services: **api-gateway**, **currency-exchange**, and **currency-conversion**.

```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

management.tracing.export.enabled=true
management.opentelemetry.tracing.export.otlp.endpoint=http://localhost:4318/v1/traces
```

## Step 3 — `InstallOpenTelemetryAppender.java`

Added the `InstallOpenTelemetryAppender.java` class to each of the three services **api-gateway**, **currency-exchange**, and **currency-conversion**.

```java
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InstallOpenTelemetryAppender implements InitializingBean {

    private final OpenTelemetry openTelemetry;

    InstallOpenTelemetryAppender(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void afterPropertiesSet() {
        OpenTelemetryAppender.install(this.openTelemetry);
    }
}

```

This class programmatically installs the OpenTelemetry Logback appender at application startup, ensuring that logs emitted via SLF4J/Logback are forwarded through the OpenTelemetry pipeline to the configured exporter (e.g., Loki via OTel Collector).

**Location:** Added in the main application package of each service:
- `api-gateway/src/main/java/.../InstallOpenTelemetryAppender.java`
- `currency-exchange/src/main/java/.../InstallOpenTelemetryAppender.java`
- `currency-conversion/src/main/java/.../InstallOpenTelemetryAppender.java`

---

## Step 4 — `logback-spring.xml`

Added `logback-spring.xml` to `src/main/resources` in all three services **api-gateway**, **currency-exchange**, and **currency-conversion**.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <include resource="org/springframework/boot/logging/logback/base.xml"/>

    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- OpenTelemetry Appender -->
    <appender name="OTEL" class="io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="OTEL"/>
    </root>

</configuration>
```

This Logback configuration wires up the OpenTelemetry appender so that all application log output is:
1. Printed to the console (standard output).
2. Forwarded to the OTel Collector via the `OpenTelemetryAppender`.

**Location:**
- `api-gateway/src/main/resources/logback-spring.xml`
- `currency-exchange/src/main/resources/logback-spring.xml`
- `currency-conversion/src/main/resources/logback-spring.xml`

---

## Step 5 — Docker Compose (`backup/docker-compose-05-zipkin.yaml`)

Extended the Docker Compose file with the following new services:

### OpenTelemetry Collector
```yaml
otel-collector:
  image: otel/opentelemetry-collector-contrib:latest
  command: ["--config=/etc/otelcol/config.yaml"]
  volumes:
    - ./otel-collector-config.yaml:/etc/otelcol/config.yaml
  ports:
    - "4317:4317"   # gRPC receiver
    - "4318:4318"   # HTTP receiver
  networks:
    - currency-network
  depends_on:
    - zipkin-server
```
Central telemetry pipeline. Receives traces, metrics, and logs from all services and routes them to Zipkin, Prometheus, and Loki respectively.

---

## Step 6 — Configuration Files

The following configuration files were added to the `backup/` directory alongside the Docker Compose file:

### `otel-collector-config.yaml`
Configures the OpenTelemetry Collector pipeline:
- **Receivers:** OTLP (gRPC on `4317`, HTTP on `4318`)
- **Processors:** Batch processor for efficiency
- **Exporters:** Zipkin (traces), Prometheus (metrics), Loki (logs)

### `prometheus.yml`
Configures Prometheus scrape targets — points to the `/actuator/prometheus` endpoints of:
- `api-gateway`
- `currency-exchange`
- `currency-conversion`

---

## Step 7 — Removed Conflicting Dependency

Removed the following dependency from `pom.xml` across affected services to resolve a conflict encountered during `mvn spring-boot:build-image`:

```xml
<!-- REMOVED — caused build-image conflict, under investigation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

> ⚠️ **Note:** This dependency conflict is under investigation. It will be re-evaluated and either re-added with the correct scope/exclusions or replaced with an alternative test dependency once the root cause is identified.

---

## Telemetry Data Flow

```
Microservices (api-gateway, currency-exchange, currency-conversion)
        │
        │  OTLP (gRPC/HTTP)
        ▼
OTel Collector
   ├──► Zipkin         (Traces)     → http://localhost:9411
                                           │
                                           ▼
                                       Grafana           → http://localhost:3000
```

---

## Running the Stack

```bash
# From the backup/ directory
docker-compose -f docker-compose-05-zipkin.yaml up -d
```

Verify all containers are healthy:
```bash
docker-compose -f docker-compose-05-zipkin.yaml ps
```

---

## Service Ports Summary

| Service | Port | URL |
|---|---|---|
| API Gateway | `8765` | `http://localhost:8765` |
| Currency Exchange | `8000` | `http://localhost:8000` |
| Currency Conversion | `8100` | `http://localhost:8100` |
| Zipkin | `9411` | `http://localhost:9411` |
| Prometheus | `9090` | `http://localhost:9090` |
| Grafana | `3000` | `http://localhost:3000` |
| Loki | `3100` | `http://localhost:3100` |
| OTel Collector (gRPC) | `4317` | — |
| OTel Collector (HTTP) | `4318` | — |

### Grafana Dashboard Configuration Guide
### Loki · Traces · Metrics
> Observability Stack: OpenTelemetry · Prometheus · Zipkin

---

## Overview

This guide walks through configuring Grafana to visualize all three telemetry signals emitted by the **api-gateway**, **currency-exchange**, and **currency-conversion** microservices.

| Setting           | Value                      |
|-------------------|----------------------------|
| Grafana URL       | http://localhost:3000       |
| Default user      | `admin`                    |
| Default password  | `admin`                    |
| Loki URL          | http://loki:3100            |
| Prometheus URL    | http://prometheus:9090      |
| Zipkin URL        | http://zipkin-server:9411   |

---

## Configure Loki (Log Aggregation)

Loki stores structured logs forwarded from the OpenTelemetry Collector. Grafana's Loki data source lets you query and explore those logs using LogQL.

---

### Add the Loki Data Source

**Step 1: Open the Data Sources panel**

- Log in to Grafana at `http://localhost:3000`
- Click the ☰ menu → **Connections** → **Data Sources**
- Click **+ Add new data source**

> `Connections → Data Sources → Add data source`
> - Left sidebar: Connections > Data Sources
> - Top-right button: `+ Add new data source`
> - Search field: type `loki` to filter

---

**Step 2: Select Loki**

- In the search box type `loki`
- Click the **Loki** tile

> Loki tile in the data source list
> - Loki card with the Grafana Loki logo
> - Description: *"Like Prometheus, but for logs"*

---

**Step 3: Fill in the Loki connection details**

| Field   | Value             |
|---------|-------------------|
| Name    | `Loki`            |
| URL     | `http://loki:3100` |
| Auth    | *(none — leave blank)* |
| Timeout | `60s` *(optional)* |

> Loki data source configuration form
> - Name field: `Loki`
> - URL field: `http://loki:3100`
> - Auth section: all toggles OFF
> - Bottom: `Save & Test` button

---

**Step 4: Save & Test**

- Click **Save & Test**
- A green banner confirming **Data source connected and labels found** should appear

>  Successful Loki connection test
> - Green alert banner: `Data source connected and labels found`
> - Details show the number of labels returned from `/loki/api/v1/labels`

---

### Explore Logs in Grafana

**Step 5: Open Explore**

- Click the **Explore** icon (compass) in the left sidebar
- In the top-left dropdown, select **Loki** as the data source

>  Explore view with Loki selected
> - Data source picker (top-left): `Loki`
> - Query builder mode (Label filters, Operations, etc.)
> - Time range picker (top-right)

---

**Step 6: Build a LogQL query**

- Switch to **Code** mode and enter a LogQL query, for example:

```logql
{service_name="currency-exchange"}
```

- Or filter by log level:

```logql
{service_name="api-gateway"} |= "ERROR"
```

- Click **Run Query** (`Shift + Enter`)

>  Loki log stream results in Explore
> - Log rows with timestamps on the left
> - Log level badges (INFO, WARN, ERROR) colour-coded
> - Expandable log line showing `trace_id`, `span_id`, `service` fields
> - *Derived fields* link out to Zipkin trace if `trace_id` is present

---

**Step 7: Enable Trace to Log correlation** *(optional)*

- Go to **Connections → Data Sources → Loki → (edit)**
- Scroll to **Derived fields**
- Click **+ Add** and fill in:

| Field         | Value                                                       |
|---------------|-------------------------------------------------------------|
| Name          | `TraceID`                                                   |
| Regex         | `traceId=(\w+)`                                             |
| URL           | `http://localhost:9411/zipkin/traces/${__value.raw}`        |
| Internal link | OFF *(external Zipkin link)*                               |

- **Save** — now clicking a trace ID inside a log line opens Zipkin directly

---

### Create a Logs Dashboard Panel

**Step 8: Add a Logs panel to a dashboard**

- Navigate to **Dashboards → New → New Dashboard**
- Click **+ Add visualization**
- Select **Loki** as the data source
- Choose **Logs** as the panel type *(top-right panel type picker)*
- Enter query:

```logql
{service_name=~"api-gateway|currency-exchange|currency-conversion"}
```

- Set **Title** to `Application Logs`
- Click **Apply → Save dashboard**

>  Logs panel in dashboard edit mode
> - Visualization picker: `Logs` selected
> - Query field with `service_name` regex
> - Logs panel options: Deduplication, Order (Newest first)
> - Live tail toggle (top-right of panel)

---

## Configure Traces (Zipkin)

Distributed traces are exported to Zipkin. Grafana can connect to Zipkin as a data source, allowing trace search and flame-graph visualisation directly from Grafana. Alternatively you can browse Zipkin's own UI at `http://localhost:9411`.

---

### Add the Zipkin Data Source

**Step 1: Add a new data source**

- **Connections → Data Sources → + Add new data source**
- Search for **Zipkin** and click the tile

>  Zipkin data source tile
> - Zipkin logo with description
> - Under category: `Tracing`

---

**Step 2: Configure the Zipkin URL**

| Field | Value                        |
|-------|------------------------------|
| Name  | `Zipkin`                     |
| URL   | `http://zipkin-server:9411`  |

> Zipkin data source configuration
> - URL: `http://zipkin-server:9411`
> - All auth off
> - `Save & Test` button at the bottom

---

**Step 3: Save & Test**

- Click **Save & Test** — you should see `Data source is working`

---

### Explore Traces

**Step 4: Search traces in Explore**

- Click **Explore** (compass icon) in the left sidebar
- Select **Zipkin** from the data source dropdown
- In the Query tab, choose a **Service** (e.g. `currency-conversion`)
- Optionally filter by Span name, Min/Max duration, Tags
- Click **Run Query**

>  Zipkin trace search in Grafana Explore
> - Service dropdown: `currency-conversion`
> - Span name dropdown (auto-populated)
> - Trace result list: trace ID, duration, span count
> - Each row is clickable → opens flame graph

---

**Step 5: Inspect a single trace**

- Click any trace row to expand the **Trace** view
- The flame graph shows each span: service name, duration, error status
- Click a span to see tags: `http.method`, `http.url`, `db.statement`, etc.

>   Trace flame graph view
> - Horizontal bars representing spans, nested by parent-child
> - Color-coded by service: api-gateway (blue), currency-exchange (green), currency-conversion (orange)
> - Right panel: span details (tags, process, annotations)
> - Total trace duration shown at the top

---

### Add a Trace Panel to a Dashboard

**Step 6: Create a Traces dashboard panel**

- Open your dashboard → **+ Add visualization**
- Data source: **Zipkin**
- Panel type: **Traces**
- Query: Service = `api-gateway`, limit = `20`
- Title: `Recent Traces`
- **Apply & Save**

>  Traces panel in dashboard
> - Table of recent traces: Trace ID, Root span, Duration, Timestamp
> - Clicking a row opens the flame graph in a drawer
> - Panel links to Zipkin UI for full detail

---

### Linking Logs → Traces

When a log line contains a `traceId` field (injected automatically by OpenTelemetry), you can jump from the log entry directly to the matching trace.

**Step 7: Configure Trace to Logs in Zipkin data source**

- Go to **Connections → Data Sources → Zipkin → (edit)**
- Scroll to **Trace to logs** section

| Field                | Value            |
|----------------------|------------------|
| Data source          | `Loki`           |
| Span start time shift | `-1m`           |
| Span end time shift  | `1m`             |
| Tags                 | `service.name`   |
| Map tag names        | `service.name → service_name` |
| Filter by trace ID   | ON               |

- **Save** — now clicking a trace in Explore shows a *"Logs for this span"* button

---

## Configure Metrics (Prometheus)

Prometheus scrapes the `/actuator/prometheus` endpoint of each microservice and stores time-series metrics. Grafana reads those metrics using PromQL.

---

###  Add the Prometheus Data Source

**Step 1: Add a new data source**

- **Connections → Data Sources → + Add new data source**
- Search for **Prometheus** and click the tile

>  Prometheus data source tile
> - Prometheus logo
> - Under category: `Metrics`

---

**Step 2: Configure the Prometheus URL**

| Field           | Value                    |
|-----------------|--------------------------|
| Name            | `Prometheus`             |
| URL             | `http://prometheus:9090` |
| Scrape interval | `15s`                    |
| Query timeout   | `60s`                    |
| HTTP method     | `POST`                   |

> Prometheus data source configuration
> - URL: `http://prometheus:9090`
> - Scrape interval: `15s`
> - Exemplars section *(optional, for linking to traces)*
> - `Save & Test` button

---

**Step 3: Save & Test**

- Click **Save & Test** — you should see `Data source is working`

---

### Enable Exemplars (Metrics → Traces)

Exemplars are data points within a metric that include a trace ID, allowing you to jump from a slow P99 spike directly into the corresponding trace.

**Step 4: Configure Exemplars in the Prometheus data source**

- While on the Prometheus data source edit page, scroll to **Exemplars**
- Click **+ Add Exemplar**

| Field         | Value     |
|---------------|-----------|
| Internal link | ON        |
| Data source   | `Zipkin`  |
| Label name    | `traceID` |

- **Save**

>  Exemplars configuration in Prometheus data source
> - Toggle: `Internal link` ON
> - Data source picker: `Zipkin`
> - Label name: `traceID`

---

### Explore Metrics

**Step 5: Browse metrics in Explore**

- **Explore → select Prometheus**
- Use the Metrics browser to pick a metric, e.g.:

```promql
http_server_requests_seconds_count{service="currency-exchange"}
```

- Switch to **Code** mode for free-form PromQL

>  Prometheus Explore view
> - Metrics browser dropdown listing all scraped metrics
> - Label filters: `job`, `service`, `method`, `status`
> - Time-series graph below query builder
> - Table view toggle

---

### Import the Spring Boot Dashboard

Grafana's dashboard library has a ready-made Spring Boot dashboard (**ID 12900**) that visualises all standard Micrometer metrics out of the box.

**Step 6: Import dashboard ID 12900**

- Click **Dashboards → New → Import**
- In the *Import via grafana.com* field, enter `12900`
- Click **Load**
- In the Prometheus data source dropdown, choose your Prometheus source
- Click **Import**

> Dashboard import screen
> - `Import via grafana.com` input field with `12900`
> - Load button
> - After load: dashboard name, folder, data source mapping

---

**Step 7: Explore the imported dashboard**

- Panels included: JVM memory, CPU, Garbage Collection, HTTP request rate, P99 latency, Active threads, Datasource pool, Uptime
- Use the **service dropdown** at the top to switch between `api-gateway`, `currency-exchange`, `currency-conversion`

> Spring Boot Grafana dashboard (ID 12900)
> - Row 1: JVM Memory (heap / non-heap), GC pause rate
> - Row 2: CPU usage, Thread count, Uptime
> - Row 3: HTTP request rate (req/s), Error rate (%)
> - Row 4: Request duration heatmap, P99 latency
> - Top: variable dropdowns — Application, Instance

---

### Build a Custom Metrics Dashboard

**Step 8: Create a request-rate panel**

- **Dashboards → New → New Dashboard → + Add visualization**
- Data source: **Prometheus** | Panel type: **Time series**
- Query:

```promql
rate(http_server_requests_seconds_count[1m])
```

- Legend: `{{service}} {{method}} {{uri}} {{status}}`
- Title: `HTTP Request Rate (req/s)`

---

**Step 9: Create a P99 latency panel**

- Add visualization → **Time series**
- Query:

```promql
histogram_quantile(0.99, sum(rate(http_server_requests_seconds_bucket[5m])) by (le, service, uri))
```

- Unit: `seconds (s)` — set under **Standard options → Unit**
- Title: `P99 Request Latency`

---

**Step 10: Create an error rate panel**

- Add visualization → **Stat** *(single number)*
- Query:

```promql
sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m]))
  / sum(rate(http_server_requests_seconds_count[1m])) * 100
```

- Unit: `Percent (0–100)`
- Thresholds: Green `< 1%` · Yellow `1–5%` · Red `> 5%`
- Title: `HTTP 5xx Error Rate`

> Custom metrics dashboard with three panels
> - Panel 1 (top-left): Request Rate — line chart, one line per service
> - Panel 2 (top-right): P99 Latency — line chart in seconds
> - Panel 3 (bottom): Error Rate — stat panel with colour threshold

---

## Unified Observability Dashboard

Combine all three signals into a single pane of glass so you can correlate a latency spike with an error log and the matching trace in one view.

**Step 1: Create a new dashboard**

- **Dashboards → New → New Dashboard**
- Save as `Microservices Observability`

---

**Step 2: Add the following rows and panels**

| Row / Panel   | Type / Data Source          | Query                                                                 |
|---------------|-----------------------------|-----------------------------------------------------------------------|
| Request Rate  | Time series / Prometheus    | `rate(http_server_requests_seconds_count[1m])`                        |
| Error Rate    | Stat / Prometheus           | `sum(rate(...{status=~"5.."}[1m])) / sum(rate(...[1m])) * 100`       |
| P99 Latency   | Time series / Prometheus    | `histogram_quantile(0.99, ...)`                                       |
| JVM Heap      | Time series / Prometheus    | `jvm_memory_used_bytes{area="heap"}`                                  |
| App Logs      | Logs panel / Loki           | `{service_name=~"api-gateway\|currency-exchange\|currency-conversion"}` |
| Error Logs    | Logs panel / Loki           | `{service_name=~"..."} \|= "ERROR"`                                   |
| Traces        | Traces panel / Zipkin       | Service = `api-gateway`, limit = `20`                                 |

>  Unified Observability Dashboard — full view
> - Row 1 (Metrics): Request Rate | Error Rate | P99 Latency | JVM Heap
> - Row 2 (Logs): All application logs | Error-only log stream
> - Row 3 (Traces): Recent traces table (clicking a row opens flame graph)
> - Top toolbar: time range picker, service variable dropdown, refresh interval

---

**Step 3: Save and set refresh interval**

- Click the save icon (top right)
- In dashboard settings, set **Auto-refresh** to `30s`
- Share the dashboard URL with your team

---

## Setting Up Alerts (Optional)

Grafana's built-in alerting can notify you when error rate or latency exceeds a threshold.

### Create an Alert Rule

**Step 1: Open the Alerting menu**

- Left sidebar → **Alerting → Alert rules → + New alert rule**

> New alert rule editor
> - Section A: Set query & alert condition
> - Section B: Alert evaluation behaviour (pending period, group, folder)
> - Section C: Annotations & labels
> - Section D: Notification policies

---

**Step 2: Define the alert condition**

- Name: `High Error Rate`
- Data source: **Prometheus**

```promql
sum(rate(http_server_requests_seconds_count{status=~"5.."}[5m]))
  / sum(rate(http_server_requests_seconds_count[5m])) * 100
```

- Condition: **IS ABOVE** `5` *(i.e. 5% error rate)*
- Pending period: `2m` *(must be true for 2 minutes before firing)*

---

**Step 3: Add a notification channel**

- **Alerting → Contact points → + Add contact point**
- Supported channels: Email, Slack, PagerDuty, Webhook
- Assign the contact point to the **Default notification policy**

---

## Quick Reference — Useful Queries

### Loki (LogQL)

```logql
# All logs from currency-exchange
{service_name="currency-exchange"}

# Error logs across all services
{service_name=~"api-gateway|currency-exchange|currency-conversion"} |= "ERROR"

# JSON-parsed logs with custom output format
{service_name="api-gateway"} | json | line_format "{{.message}} traceId={{.traceId}}"
```

### Prometheus (PromQL)

```promql
# Per-second request rate over last 1 minute
rate(http_server_requests_seconds_count[1m])

# P95 latency per service
histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le, service))

# JVM heap utilisation %
jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"} * 100

# CPU usage % per service instance
process_cpu_usage * 100
```

### Useful Dashboard IDs (grafana.com)

| ID    | Description                                        |
|-------|----------------------------------------------------|
| 12900 | Spring Boot 2.1 Statistics — JVM, HTTP, Datasource |
| 4701  | JVM (Micrometer) — detailed GC and memory          |
| 11378 | Spring Boot Actuator — full Micrometer coverage    |
| 13332 | Loki Logs Overview dashboard                       |

---

Happy Learning @in28minutes