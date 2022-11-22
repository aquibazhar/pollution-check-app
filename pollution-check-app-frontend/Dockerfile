# STAGE 1
FROM node:16.13.0-alpine AS builder
COPY . /pollution-app
WORKDIR /pollution-app
RUN npm install
RUN npm run build

# STAGE 2
FROM nginx:1.17.10-alpine
EXPOSE 80
COPY --from=builder /pollution-app/dist/pollution-check /usr/share/nginx/html