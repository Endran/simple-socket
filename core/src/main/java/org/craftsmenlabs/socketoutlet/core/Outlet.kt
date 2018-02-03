/*
 * Copyright (c) 2017 David Hardy.
 * Copyright (c) 2017 Craftsmenlabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.craftsmenlabs.socketoutlet.core

abstract class Outlet<T>(val clazz: Class<T>) {

    @Suppress("UNCHECKED_CAST")
    fun onTypelessMessage(typelessObject: Any, egress: ((Any) -> Unit)) {
        try {
            onMessage(typelessObject as T, object : Egress {
                override fun send(message: Any) {
                    egress.invoke(message)
                }
            })
        } catch (t: Throwable) {
            egress.invoke(ErrorMessage("An error occurred when invoking ${this.javaClass.name}.onMessage(...)", t))
        }
    }

    protected abstract fun onMessage(message: T, egress: Egress)
}
