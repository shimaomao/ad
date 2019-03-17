package com.planx.pay.bitcoin;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;
import org.springframework.stereotype.Component;

@Component("bitcoinSentEventListener")
public class BitcoinSentEventListener implements WalletCoinsSentEventListener {

	@Override
	public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
		// TODO Auto-generated method stub

	}

}
