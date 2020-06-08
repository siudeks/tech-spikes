1. Dockerfile
  COPY --from=build-dependencies /src/.host /app
  CMD ["sh". "/app/host-startup.sh"]

1. host-startup.sh
  cd /usr/share/nginx/html
  echo var AAD_CLIENT_ID=\'$AAD_CLIENT_ID\'\; > set-env-vars.js
  nginx -g 'daemon off;'

1. index.html
  <body>
    <script src='set-env-vars.js'>
    </script>

1. App.tsx
const aadClientId = (window as any).AAD_CLIENT_ID || 'default value'