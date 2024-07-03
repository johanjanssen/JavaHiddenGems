FROM eclipse-temurin:17.0.3_7-jdk as java
WORKDIR /mirror
RUN apt-get update && apt-get install wget

SHELL ["/bin/bash", "-c"]
# From https://github.com/jeremylong/Open-Vulnerability-Project/tree/main/vulnz#caching-the-nvd-cve-data
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.json.gz
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.meta
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2023.json.gz
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2022.json.gz
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2021.json.gz
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2020.json.gz
#RUN wget https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-$(date +%Y).json.gz

RUN wget https://github.com/jeremylong/Open-Vulnerability-Project/releases/download/v5.0.3/vulnz-5.0.3.jar
RUN chmod +x vulnz-5.0.3.jar
RUN ./vulnz-5.0.3.jar cve --cache --directory .

RUN wget --no-check-certificate https://www.cisa.gov/sites/default/files/feeds/known_exploited_vulnerabilities.json

# Old GroupIds Alerter
RUN wget https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json --no-check-certificate
RUN wget https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-unofficial-definitions.json --no-check-certificate

FROM nginx:1.27.0-alpine
COPY --from=java /mirror /usr/share/nginx/html