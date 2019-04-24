# Financing

## Goal with Financing

As Open-Registry is a Open Service, one requirement is that the finances are
open to public to inspect. The details will be visible both on the homepage
and on the metrics dashboard, where people can drill down into the details if
they so wish.

## Treasurer
> For more details, see the document about Governance

The Treasurer is elected by votes and is responsible for the donation pipeline
and making sure expenses are paid.

## Transparency

No funds should enter/leave the account without knowing where it comes from and
where it's going. It should also be viewable by the public.

## Payment Methods

Currently, funding happens via LiberaPay under the username `Open-Registry-Community`.

https://liberapay.com/Open-Registry-Community/

The LiberaPay account is connected with Stripe and PayPal and goes to the current
treasurer who is responsible for payments.

We hope to replace this with a OpenCollective in the future.

# Financing API

> Notes for developers who implement the metrics API for financing

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

Funding

https://stripe.com/docs/api/payouts

https://stripe.com/docs/api/balance

### Possible Services

The following services are to be connected to get the full picture of the financing

- Paypal - Actual incoming donations
- Stripe - Actual incoming donations
- LiberaPay - Expected incoming donations
- OpenCollective - Expected incoming donations
- Hetzner - Show expected + actual costs
- DNSimple - Actual costs

## Metrics to include

- How much the organization costs in total
- Where does that cost go to exactly
- How much we pay in fees/tax on donations
- How much is donated right now
- How much of that has been spent
- On what has that been spent?

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
