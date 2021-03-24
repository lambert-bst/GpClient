/*
 * Copyright 2019 Patrick Ahlbrecht
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.onyxbits.raccoon.gplay;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akdeniz.googleplaycrawler.DownloadData;
import com.akdeniz.googleplaycrawler.GooglePlayAPI;

import de.onyxbits.raccoon.db.DatabaseManager;
import de.onyxbits.raccoon.repo.Layout;

public class DownloadTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException, IOException {
		DatabaseManager dbm = new DatabaseManager(Layout.DEFAULT.databaseDir);
		PlayManager pm = new PlayManager(dbm);
		GooglePlayAPI api = pm.createConnection();
		//DownloadData data = api.purchaseAndDeliver("com.irccloud.android",207,1);
		DownloadData data = api.purchaseAndDeliver("onyxbits.de.testapp",2,1);
		System.err.println(data);
	}

}
