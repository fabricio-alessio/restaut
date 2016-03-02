/*
 * Copyright 2015 MongoDB, Inc.
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

package br.com.restautomation.user;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class UserDAO {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final MongoCollection<Document> coll;

	public UserDAO(final MongoDatabase blogDatabase) {
		coll = blogDatabase.getCollection("users");
	}

	public void save(Document doc) {

		try {
			coll.insertOne(doc);
		} catch (MongoWriteException e) {
			logger.error("Não foi possível salvar o usuário", e);
		}
	}

	public Document findById(String id) {

		return coll.find(Filters.eq("_id", id)).first();
	}
}
