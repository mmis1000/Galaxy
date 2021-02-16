/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2021
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.oktw.galaxy.network

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.charset.StandardCharsets
import java.time.Duration

class LocalPublisher {
    companion object {
        fun publish(motd: String, addressPort: String): Job {
            val string = createAnnouncement(motd, addressPort)
            val bs = string.toByteArray(StandardCharsets.UTF_8)
            val socket = DatagramSocket()

            return GlobalScope.launch {
                while (true) {
                    try {
                        val inetAddress = InetAddress.getByName("224.0.2.60")
                        val datagramPacket = DatagramPacket(bs, bs.size, inetAddress, 4445)
                        if (coroutineContext.isActive) {
                            socket.send(datagramPacket)
                        }
                    } catch (e: Exception) {
                        println(e)
                    }

                    delay(Duration.ofMillis(1500))
                }
            }
        }

        private fun createAnnouncement(motd: String, addressPort: String): String {
            return "[MOTD]$motd[/MOTD][AD]$addressPort[/AD]"
        }
    }
}
