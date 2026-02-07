FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Production stage - use a lightweight Node static server instead of nginx
FROM node:20-alpine AS production

WORKDIR /app
COPY --from=build /app/dist ./dist

# Install a simple static server to serve the built SPA
RUN npm install -g serve@14.1.2 --silent

EXPOSE 80

# Serve the SPA and let it handle client-side routes
CMD ["serve", "-s", "dist", "-l", "80"]