version: '3'
services:
  discovery:
    image: consul:1.9
    ports:
      - 8500:8500

  service-auth:
    image: steam/service-auth
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery
    depends_on:
      - discovery

  gateway:
    image: steam/gateway
    ports:
      - 8080:8080
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery
    depends_on:
      - discovery

  micro-vente:
    image: steam/micro-vente
    restart: always
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery -DCONSUL_PORT=8500
    depends_on:
      - discovery
      - service-auth

  micro-client:
    image: steam/micro-social
    restart: always
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery -DCONSUL_PORT=8500
    depends_on:
      - discovery
      - service-auth

  micro-social:
    image: steam/micro-social
    restart: always
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery -DCONSUL_PORT=8500
    depends_on:
      - discovery
      - service-auth

  micro-catalogue:
    image: steam/micro-catalogue
    restart: always
    environment:
      - JAVA_TOOL_OPTIONS=
        -DCONSUL_HOST=discovery -DCONSUL_PORT=8500
    depends_on:
      - discovery
      - service-auth
