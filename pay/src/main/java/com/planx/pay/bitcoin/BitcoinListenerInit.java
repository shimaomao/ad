package com.planx.pay.bitcoin;

import javax.annotation.PostConstruct;

import org.bitcoinj.core.listeners.TransactionConfidenceEventListener;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitcoinListenerInit {

	@Autowired
	private WalletAppKit appkit;

	@Autowired
	@Qualifier("bitcoinReceivedEventListener")
	private WalletCoinsReceivedEventListener bitcoinReceivedEventListener;

	@Autowired
	@Qualifier("bitcoinConfidenceEventListener")
	private TransactionConfidenceEventListener bitcoinConfidenceEventListener;

	@Autowired
	@Qualifier("bitcoinKeyChainEventListener")
	private KeyChainEventListener bitcoinKeyChainEventListener;

	@Autowired
	@Qualifier("bitcoinSentEventListener")
	private WalletCoinsSentEventListener bitcoinSentEventListener;

	@PostConstruct
	public void init() {

		appkit.wallet().addCoinsReceivedEventListener(bitcoinReceivedEventListener);
		appkit.wallet().addKeyChainEventListener(bitcoinKeyChainEventListener);
		appkit.wallet().addTransactionConfidenceEventListener(bitcoinConfidenceEventListener);
		appkit.wallet().addCoinsSentEventListener(bitcoinSentEventListener);
        
	}
}
