package com.planx.pay.bitcoin;

import java.util.List;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.springframework.stereotype.Component;

@Component("bitcoinKeyChainEventListener")
public class BitcoinKeyChainEventListener implements KeyChainEventListener {

	@Override
	public void onKeysAdded(List<ECKey> keys) {
		// TODO Auto-generated method stub

	}

}
