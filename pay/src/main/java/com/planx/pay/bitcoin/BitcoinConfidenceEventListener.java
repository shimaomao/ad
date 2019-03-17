package com.planx.pay.bitcoin;

import java.math.BigDecimal;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.listeners.TransactionConfidenceEventListener;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.planx.pay.service.BitcoinRechargeService;

@Component("bitcoinConfidenceEventListener")
public class BitcoinConfidenceEventListener implements TransactionConfidenceEventListener {

	private static final double SATOSHI_PER_COIN = Math.pow(10L, 8);

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BitcoinRechargeService bitcoinRechargeService;

	@Override
	public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
		TransactionConfidence confidence = tx.getConfidence();
		// DepthInBlocks increase about every 10 minutes. So, it will be checked once about 12 hours
		// first check will be in 6 * 10 minutes
		if (confidence.getDepthInBlocks() % (6 * 12) != 6) {
			return;
		}
		log.info("onTransactionConfidenceChanged: " + tx.getHashAsString());
		if (tx.getValue(wallet).isGreaterThan(Coin.ZERO)) {
			tx.getOutputs().stream().filter(output -> output.isMine(wallet)).forEach(txOut -> {
				String txId = tx.getHashAsString();
				String bitcoinAddress = txOut.getScriptPubKey().getToAddress(wallet.getNetworkParameters()).toString();
				BigDecimal bitcoinAmount = new BigDecimal(txOut.getValue().getValue()).divide(BigDecimal.valueOf(SATOSHI_PER_COIN));
				bitcoinRechargeService.bitcoinReceive(txId, bitcoinAddress, bitcoinAmount);
				log.info("onTransactionConfidenceChanged address: {}, amount: {}", bitcoinAddress, bitcoinAmount);

			});
		}

	}

}
