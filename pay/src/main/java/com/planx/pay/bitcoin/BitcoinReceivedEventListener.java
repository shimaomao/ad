package com.planx.pay.bitcoin;

import java.math.BigDecimal;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.planx.pay.service.BitcoinRechargeService;

@Component("bitcoinReceivedEventListener")
public class BitcoinReceivedEventListener implements WalletCoinsReceivedEventListener {

	private static final double SATOSHI_PER_COIN = Math.pow(10L, 8);

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BitcoinRechargeService bitcoinRechargeService;

	@Override
	public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
		if (tx.getValue(wallet).isGreaterThan(Coin.ZERO)) {
			log.info("onCoinsReceived: " + tx.getHashAsString());
			tx.getOutputs().stream().filter(output -> output.isMine(wallet)).forEach(txOut -> {
				String txId = tx.getHashAsString();
				String bitcoinAddress = txOut.getScriptPubKey().getToAddress(wallet.getNetworkParameters()).toString();
				BigDecimal bitcoinAmount = new BigDecimal(txOut.getValue().getValue()).divide(BigDecimal.valueOf(SATOSHI_PER_COIN));
				bitcoinRechargeService.bitcoinReceive(txId, bitcoinAddress, bitcoinAmount);
				log.info("onCoinsReceived address: {}, amount: {}", bitcoinAddress, bitcoinAmount);

			});
		}
	}

}
