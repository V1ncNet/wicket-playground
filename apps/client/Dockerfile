FROM node:20.18.0-alpine AS base

FROM base AS dependencies
WORKDIR /usr/src/playground

RUN apk add --no-cache libc6-compat

ENV NO_COLOR=true

COPY package.json package-lock.json ./

RUN npm ci


FROM base AS builder
WORKDIR /usr/src/playground

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DISABLED=1

COPY --from=dependencies /usr/src/playground/node_modules ./node_modules
COPY . .


RUN npm run build


FROM base AS runner
WORKDIR /opt/playground

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DISABLED=1

RUN addgroup --system --gid 1001 nodejs
RUN adduser  --system --uid 1001 nextjs

COPY --from=builder                       /usr/src/playground/public           ./public

RUN mkdir .next
RUN chown nextjs:nodejs .next

COPY --from=builder --chown=nextjs:nodejs /usr/src/playground/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /usr/src/playground/.next/static     ./.next/static

USER nextjs

EXPOSE 3000
ENV PORT=3000
ENV HOSTNAME=0.0.0.0

CMD ["node", "server.js"]
