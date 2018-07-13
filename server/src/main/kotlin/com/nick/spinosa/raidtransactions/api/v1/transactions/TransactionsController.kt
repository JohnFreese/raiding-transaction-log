package com.nick.spinosa.raidtransactions.api.v1.transactions

import org.springframework.beans.factory.annotation.Autowired
import com.nick.spinosa.raidtransactions.daos.TransactionDao
import com.nick.spinosa.raidtransactions.entities.Instance
import com.nick.spinosa.raidtransactions.entities.Transaction
import com.nick.spinosa.raidtransactions.entities.Transgression
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionsController() {

    @Autowired
    lateinit var transactionDao: TransactionDao

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): Transaction {
        return transactionDao.findById(id).get()
    }

    @PostMapping()
    fun createOrUpdate(@RequestBody raider: Transaction) {
        transactionDao.save(raider)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        transactionDao.deleteById(id)
    }

    @GetMapping()
    fun getAll(@RequestParam(name = "page", defaultValue = "1") pageNumber: Int,
               @RequestParam(name = "page-size", defaultValue = "10") pageSize: Int,
               @RequestParam(name = "filter", defaultValue = "null") filter: String,
               @RequestParam(name = "parameter", defaultValue = "null") parameter: String,
               @RequestParam(name = "raiderId", defaultValue = "null") raiderId: Long?,
               @RequestParam(name = "instance", defaultValue = "null") instance: Instance?,
               @RequestParam(name = "transgression", defaultValue = "null") transgression: Transgression?,
               @RequestParam(name = "date", defaultValue = "null") date: Timestamp?,
               @RequestParam(name = "gold", defaultValue = "null") gold: Long?,
               @RequestParam(name = "silver", defaultValue = "null") silver: Long?,
               @RequestParam(name = "copper", defaultValue = "null") copper: Long?): List<Transaction> {

        return transactionDao.findAll(PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.ASC, "raider")).content
        //return transactionDao.findAllById(raiderId, instance, transgression, date, gold, silver, copper,
        //        PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.ASC, "raider")).content
    }
}