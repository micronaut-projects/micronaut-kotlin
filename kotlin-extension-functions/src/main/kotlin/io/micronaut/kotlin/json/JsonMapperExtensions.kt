/*
 * Copyright 2017-2026 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.kotlin.json

import io.micronaut.core.annotation.Experimental
import io.micronaut.core.io.buffer.ReadBuffer
import io.micronaut.json.JsonMapper
import io.micronaut.json.tree.JsonNode
import io.micronaut.kotlin.http.argumentOf
import java.io.IOException
import java.io.InputStream

/**
 * Transform a [JsonNode] to a value of type [T].
 *
 * @param tree The input JSON data.
 * @param T Type variable of the return type.
 * @return The deserialized value.
 * @throws IOException IOException
 */
@Experimental
@Throws(IOException::class)
inline fun <reified T : Any> JsonMapper.readValueFromTree(tree: JsonNode): T? =
  readValueFromTree(tree, argumentOf<T>())

/**
 * Parse and map JSON from the given stream.
 *
 * @param inputStream The input stream.
 * @param T Type variable of the return type.
 * @return The deserialized value.
 * @throws IOException IOException
 */
@Experimental
@Throws(IOException::class)
inline fun <reified T : Any> JsonMapper.readValue(inputStream: InputStream): T? =
  readValue(inputStream, argumentOf<T>())

/**
 * Parse and map JSON from the given byte array.
 *
 * @param byteArray The input data.
 * @param T Type variable of the return type.
 * @return The deserialized value.
 * @throws IOException IOException
 */
@Experimental
@Throws(IOException::class)
inline fun <reified T : Any> JsonMapper.readValue(byteArray: ByteArray): T? =
  readValue(byteArray, argumentOf<T>())

/**
 * Parse and map JSON from the given read buffer.
 *
 * @param readBuffer The input data.
 * @param T Type variable of the return type.
 * @return The deserialized value.
 * @throws IOException IOException
 */
@Experimental
@Throws(IOException::class)
inline fun <reified T : Any> JsonMapper.readValue(readBuffer: ReadBuffer): T? =
  readValue(readBuffer, argumentOf<T>())

/**
 * Parse and map JSON from the given string.
 *
 * @param string The input data.
 * @param T Type variable of the return type.
 * @return The deserialized value.
 * @throws IOException IOException
 */
@Experimental
@Throws(IOException::class)
inline fun <reified T : Any> JsonMapper.readValue(string: String): T? =
  readValue(string, argumentOf<T>())

