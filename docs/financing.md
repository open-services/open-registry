# Financing

Right now, funding will be setup via LiberaPay. Under username `Open-Registry-Community`

Link: https://liberapay.com/Open-Registry-Community/

Currently connected to Victor Bjelkholm's Stripe + Paypal and he will act as 
treasurer until a better option appears.

All funds will go towards hosting. Excess will go towards development time, if
there is still excess, funding will be donated to LiberaPay itself.

(The above should be written into the governance)

https://liberapay.com/Open-Registry-Community/public.json outlines current funding

curl -u 'user-id:password' https://robot-ws.your-server.de/server is currently running servers

Get Traffic

curl -u user-id:password https://robot-ws.your-server.de/traffic \
       --data-urlencode 'type=month' \
       --data-urlencode 'from=2010-09-01' \
       --data-urlencode 'to=2010-09-31' \
       --data-urlencode 'ip=123.123.123.123'

DNSimple

curl  -H 'Authorization: Bearer <token>' \
      -H 'Accept: application/json' \
      https://api.dnsimple.com/v2/whoami

## finance.open-registry.dev

finance.open-registry.dev is a dedicated domain for the API of financing for Open-Registry

The root URL of the api is `finance.open-registry.dev` and there is no authentication.

Responses might include data that is up to one hour old.

### GET `/income/now`

> The current status of income

Response:

```
{
  "donators": 32,
  "liberapay": {
    "username": "Open-Registry-Community",
    "donators": 10,
    "receiving": {
      "amount": "10.32",
      "currency": "EUR"
    }
  },
  "receiving": {
    "amount": "123.23",
    "currency": "EUR"
  }
}
```

### GET `/income/day`

Response is an array of responses structured like `/income/now` but for each
day for the last 30 days

### GET `/income/week`

Same as `/income/day` but for each week the last 3 months

### GET `/income/month`

Same as `/income/day` but for each month the last 1 year

### GET `/income/year` (not to implemented yet)

Same as `/income/day` but for each year the last 3 years

### GET `/income/all`

Full history since day one, grouped by months

### GET `/expenses`
