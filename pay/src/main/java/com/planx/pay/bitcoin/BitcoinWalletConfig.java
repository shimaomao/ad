package com.planx.pay.bitcoin;

import java.io.File;
import java.io.IOException;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitcoinWalletConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String BITCOIN_TESTNET = "test";
	
	@Value("${bitcoin.network}")
	private String bitcoinNetwork;
	
	@Value("${bitcoin.wallet.dir}")
	private String bitcoinWalletDir;

	@Bean
	public WalletAppKit setupWalletKit() throws IOException {

	    NetworkParameters params = null;
	    if (BITCOIN_TESTNET.equals(bitcoinNetwork)) {
	    	params = TestNet3Params.get();
	    } else {
	    	params = MainNetParams.get();
	    }
	    log.info("init bitcoin wallet, use param: " + bitcoinNetwork);
	    String walletFileName = "wallet-" + params.getPaymentProtocolId();
	    File dir = new File(bitcoinWalletDir);
		WalletAppKit bitcoin = new WalletAppKit(params, dir, walletFileName) {
            @Override
            protected void onSetupCompleted() {
                // Don't make the user wait for confirmations for now, as the intention is they're sending it their own money!
            	this.wallet().allowSpendingUnconfirmedTransactions();
            }
        };
        if (params == RegTestParams.get()) {
            bitcoin.connectToLocalHost();
        }
        if (bitcoin.isChainFileLocked()) {
            throw new RuntimeException("ChainFileLocked");
        }
        bitcoin.startAsync();
        bitcoin.awaitRunning();
        
		return bitcoin;
	}
	
}
