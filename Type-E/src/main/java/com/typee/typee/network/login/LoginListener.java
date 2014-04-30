package com.typee.typee.network.login;

import com.parse.ParseUser;
import com.typee.typee.network.base.ErrorListener;

/**
 * Created by gengxian on 2/17/14.
 */
public interface LoginListener extends ErrorListener {

	public void successful(ParseUser parseUser);

}
