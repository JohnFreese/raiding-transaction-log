package com.nick.spinosa.raidtransactions.Controllers

import com.nick.spinosa.raidtransactions.Entities.setUpRaider
import com.nick.spinosa.raidtransactions.entities.Raider
import com.nick.spinosa.raidtransactions.typeRef
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.net.URI

@RunWith(SpringRunner::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RaidersControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun testCreate() {
        val raider1 = setUpRaider("testRaiderRaidersCreate")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        val result = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertTrue(result.body!!.map(Raider::name).contains(raider1.name))
    }

    @Test
    fun testRead() {
        val raider1 = setUpRaider("testRaiderRaidersRead")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        val raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertNotNull(raiders)

        val raider1Id = raiders.body!!.filter { raider -> raider.name == raider1.name }.first().id
        val raider = testRestTemplate.getForEntity<Raider>("/api/v1/raiders/$raider1Id")
        assertNotNull(raider)
        assertEquals(raiders.statusCode, HttpStatus.OK)
        assertTrue(raider.body!!.name == raider1.name)
    }

    @Test
    fun testDelete() {
        val raider1 = setUpRaider("testRaiderRaidersDelete")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        var raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertNotNull(raiders.body)

        val raider1Id = raiders.body!!.filter { raider -> raider.name == raider1.name }.first().id
        testRestTemplate.delete("/api/v1/raiders/$raider1Id")
        raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertFalse(raiders.body!!.map(Raider::name).contains(raider1.name))
    }

    @Test
    fun testFindAll() {
        val raider1 = setUpRaider("testRaiderRaidersFind1")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        val raider2 = setUpRaider("testRaiderRaidersFind2")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider2, Raider::class)

        val raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertNotNull(raiders.body)
        assertTrue(raiders.body!!.map(Raider::name).contains(raider1.name)
        && raiders.body!!.map(Raider::name).contains(raider2.name))
    }

    @Test
    fun testPages() {
        val raider1 = setUpRaider("testRaiderRaidersPages1")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        val raider2 = setUpRaider("testRaiderRaidersPages2")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider2, Raider::class)

        val raider3 = setUpRaider("testRaiderRaidersPages3")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider3, Raider::class)

        var raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders?page-size=2")), typeRef<List<Raider>>())
        assertTrue(raiders.body!!.size <= 2)
    }

    @Test
    fun testPageDefault() {
        val raider1 = setUpRaider("testRaidLeaderRaidersPagesDefault1")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider1, Raider::class)

        val raider2 = setUpRaider("testRaidLeaderRaidersPagesDefault2")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider2, Raider::class)

        val raider3 = setUpRaider("testRaidLeaderRaidersPagesDefault3")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider3, Raider::class)

        val raider4 = setUpRaider("testRaidLeaderRaidersPagesDefault4")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider4, Raider::class)

        val raider5 = setUpRaider("testRaidLeaderRaidersPagesDefault5")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider5, Raider::class)

        val raider6 = setUpRaider("testRaidLeaderRaidersPagesDefault6")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider6, Raider::class)

        val raider7 = setUpRaider("testRaidLeaderRaidersPagesDefault7")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider7, Raider::class)

        val raider8 = setUpRaider("testRaidLeaderRaidersPagesDefault8")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider8, Raider::class)

        val raider9 = setUpRaider("testRaidLeaderRaidersPagesDefault9")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider9, Raider::class)

        val raider10 = setUpRaider("testRaidLeaderRaidersPagesDefault10")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider10, Raider::class)

        val raider11 = setUpRaider("testRaidLeaderRaidersPagesDefault11")
        testRestTemplate.postForEntity<Raider>("/api/v1/raiders", raider11, Raider::class)

        val raiders = testRestTemplate.exchange(RequestEntity<List<Raider>>(HttpMethod.GET, URI("/api/v1/raiders")), typeRef<List<Raider>>())
        assertTrue(raiders.body!!.size == 10)
    }
}