package com.agree.ecosystem.core.utils.utility.eventbus

/**
 * Global Event Bus Engine
 * @author : @telkomdev-abdul
 * @since : 10 Sep 2022
 */
interface EventBusEngine {
    val eventBus: EventBus

    fun broadcastEvent(event: () -> AppEvent?)
}
