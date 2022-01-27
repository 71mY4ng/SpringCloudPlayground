# play-transaction

## transaction 1 - Payment

* Order -[pay]-> PaymentRecord
* PaymentRecord -[settle]-> Account

#### Order

* id
* createTime
* itemId
* itemPrice
* customerId
* shopId
* quantity
* orderPrice
* state
* lastUpdateTime

### entity

#### PaymentRecord

* id
* orderId
* orderPrice
* customerId
* accountId
* merchantAccountId
* channel
* startTime
* state
* settleTime

#### Customer

* id
* customerName
* mobileNum
* email
* registerDate

#### CustomerAccount

* id
* customerId
* accountId
* accountType
* balance
* createTime
* state
* lastUpdateTime

#### Pay

* Order -[pay]-> PaymentRecord
