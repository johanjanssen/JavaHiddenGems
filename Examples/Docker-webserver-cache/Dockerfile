FROM eclipse-temurin:17.0.3_7-jdk as java
WORKDIR /mirror
RUN apt-get update && apt-get install wget

SHELL ["/bin/bash", "-c"]
# From https://nvd.nist.gov/vuln/data-feeds
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.json.gz
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.meta
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2023.json.gz
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2022.json.gz
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2021.json.gz
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2020.json.gz
RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-$(date +%Y).json.gz
RUN wget --no-check-certificate https://www.cisa.gov/sites/default/files/feeds/known_exploited_vulnerabilities.json

# Old GroupIds Alerter
RUN wget https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json --no-check-certificate -O /mirror/og-definitions.json

FROM nginx:1.23.3-alpine
COPY --from=java /mirror /usr/share/nginx/html