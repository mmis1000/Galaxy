package one.oktw.galaxy.galaxy.planet.data.extensions

import one.oktw.galaxy.Main.Companion.galaxyManager
import one.oktw.galaxy.enums.AccessLevel
import one.oktw.galaxy.enums.AccessLevel.*
import one.oktw.galaxy.galaxy.data.extensions.getGroup
import one.oktw.galaxy.galaxy.enums.Group.VISITOR
import one.oktw.galaxy.galaxy.planet.PlanetHelper
import one.oktw.galaxy.galaxy.planet.data.Planet
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.world.World

suspend fun Planet.checkPermission(player: Player): AccessLevel {
    val group = galaxyManager.get(planet = uuid).await()?.getGroup(player) ?: return DENY

    return if (group != VISITOR) MODIFY else if (visitable) VIEW else DENY
}

fun Planet.loadWorld(): World? {
    return PlanetHelper.loadPlanet(this)
}
