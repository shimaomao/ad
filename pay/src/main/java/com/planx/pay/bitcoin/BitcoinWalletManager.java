package com.planx.pay.bitcoin;

import java.math.BigDecimal;
import java.util.List;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.KeyCrypterScrypt;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Protos;
import org.spongycastle.crypto.params.KeyParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.ByteString;

@Service
public class BitcoinWalletManager {

	public static final Protos.ScryptParameters SCRYPT_PARAMETERS = Protos.ScryptParameters.newBuilder().setP(6).setR(8)
			.setN(32768).setSalt(ByteString.copyFrom(KeyCrypterScrypt.randomSalt())).build();

	@Autowired
	private WalletAppKit appKit;

	public BigDecimal getBalance() {
		return new BigDecimal(appKit.wallet().getBalance().getValue());
	}
	
	public Address freshBitcoinAddress() {
		return appKit.wallet().freshReceiveAddress();
	}
	
	public String getSeed(String password) {
		DeterministicSeed seed = appKit.wallet().getKeyChainSeed();
		if (seed.isEncrypted()) {
			if (null == password) {
				throw new RuntimeException("Wallet is encrypted");
			}
			KeyCrypterScrypt scrypt = (KeyCrypterScrypt) appKit.wallet().getKeyCrypter();
			KeyParameter aesKey = scrypt.deriveKey(password);
			seed = seed.decrypt(appKit.wallet().getKeyCrypter(), "", aesKey);
		}
		final List<String> mnemonicCode = seed.getMnemonicCode();
		String origWords = Utils.join(mnemonicCode);
		return origWords;
	}

	public boolean encrypt(String password) {
		if (null == password || password.length() < 4) {
			return false;
		}
		KeyCrypterScrypt scrypt = new KeyCrypterScrypt(SCRYPT_PARAMETERS);
		KeyParameter aesKey = scrypt.deriveKey(password);
		appKit.wallet().encrypt(scrypt, aesKey);
		return true;
	}

	public boolean decrypt(String password) {
		KeyCrypterScrypt scrypt = (KeyCrypterScrypt) appKit.wallet().getKeyCrypter();
		KeyParameter aesKey = scrypt.deriveKey(password);
		appKit.wallet().decrypt(aesKey);
		return true;
	}
	
}
