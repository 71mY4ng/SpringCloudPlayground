-- public.m_sell_bid definition

-- Drop table

-- DROP TABLE public.m_sell_bid;

CREATE TABLE public.m_sell_bid (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	seller_id varchar NULL,
	seller_name varchar NULL,
	item_id varchar NULL,
	item_name varchar NULL,
	bid_price money NULL,
	quantity int8 NULL,
	bid_time time(0) NULL,
	bidno varchar(80) NOT NULL
);


-- public.m_deal_log definition

-- Drop table

-- DROP TABLE public.m_deal_log;

CREATE TABLE public.m_deal_log (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	deal_no varchar(80) NOT NULL,
	seller_no varchar NULL,
	seller_name varchar NULL,
	buyer_no varchar NULL,
	buyer_name varchar NULL,
	item_no varchar NULL,
	item_name varchar NULL,
	quantity int4 NULL,
	total_amount money NULL,
	deal_time timetz NULL,
	settle_time timetz NULL,
	last_update timetz NULL,
	deal_state varchar NULL
);


-- public.m_market_storage definition

-- Drop table

-- DROP TABLE public.m_market_storage;

CREATE TABLE public.m_market_storage (

);


-- public.u_account definition

-- Drop table

-- DROP TABLE public.u_account;

CREATE TABLE public.u_account (
	account_no varchar NULL,
	account_name varchar NULL,
	user_id varchar NULL,
	account_type varchar NULL,
	account_state varchar NULL,
	create_time timetz NULL
);


-- public.c_item definition

-- Drop table

-- DROP TABLE public.c_item;

CREATE TABLE public.c_item (
	id int4 NULL,
	item_no varchar NULL,
	item_name varchar NULL
);