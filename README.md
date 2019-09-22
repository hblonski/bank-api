# Bank API (moved to https://github.com/hblonski/bank-api-spring)

This is a very simple application that implements some simple bank operations.
It was built using JAX-RS, Jetty, and Jersey.

**Endpoints implemented so far:**

**Client**

*@POST client/create* - receives a JSON containing client info (see class `ClientDTO`) and creates a new client.
Returns the new client info.

**Account**

*@POST account/create* - receives a `clientId` as query param, and creates a new account for the client. Returns the
new account info.

*@GET account/{number}* - receives an account `number` and returns its info.

**Transactions**

*@GET transactions/deposit* - receives a JSON containing transaction info (see class `TransactionDTO`), which will be
used to perform a deposit operation. Returns the transaction info.

*@GET transactions/transfer* - receives a JSON containing transaction info (see class `TransactionDTO`), which will be
used to perform a transfer operation. Returns the transaction info. In a transfer, one of the accounts must belong to
the bank that owns the API. In a request, this is represented by setting `originAccountBank` or `destinationAccountBank`
to `23`.

A Postman collection containing sample requests is included in the project root.

**Endpoints still to be implemented (future work):**

- Get client info
- Get transactions (bank statement)
- Edit client info
- Delete account/client
- Money withdrawal
